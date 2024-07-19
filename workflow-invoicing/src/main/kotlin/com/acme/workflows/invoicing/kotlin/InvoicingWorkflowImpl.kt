package com.acme.workflows.invoicing.kotlin

import com.acme.services.invoice.InvoiceService
import com.acme.services.metrics.MetricsService
import com.acme.services.notification.NotificationService
import com.acme.utils.AbstractWorkflow
import com.acme.workflows.invoicing.InvoicingWorkflow
import com.acme.workflows.invoicing.User
import com.acme.workflows.payment.PaymentWorkflow
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class InvoicingWorkflowImpl: AbstractWorkflow(), InvoicingWorkflow {
    // workflow stub that targets itself
    private val self = getWorkflowById(InvoicingWorkflow::class.java, workflowId)

    // create stub for MetricsService
    private val metricsService = newService(MetricsService::class.java)

    // create stub for PaymentWorkflow
    private val paymentWorkflow = newWorkflow(PaymentWorkflow::class.java)

    // create stub for InvoiceService
    private val invoiceService = newService(InvoiceService::class.java)

    // create stub for NotificationService
    private val notificationService = newService(NotificationService::class.java)

    override fun start(user: User?) {
        // get current date
        val start = inline { LocalDateTime.now() }
        // get start of first day of next month
        val end = start.plusMinutes(1)
        // val end = start.withDayOfMonth(1).plusMonths(1).truncatedTo(ChronoUnit.DAYS)
        // wait until then
        timer(Duration.between(start, end)).await()
        // calculate how much the user will pay
        val amount = metricsService.calculateAmount(user, start, end)
        // get payment for the user
        paymentWorkflow.getPayment(user, amount)
        // generate the invoice
        val invoice = invoiceService.create(user, amount, start, end)
        // send the invoice
        notificationService.sendInvoice(user, invoice)
        // loop if the user is still subscribed
        if (metricsService.isSubscribed(user)) {
            dispatch(self::start, user)
        }
    }
}