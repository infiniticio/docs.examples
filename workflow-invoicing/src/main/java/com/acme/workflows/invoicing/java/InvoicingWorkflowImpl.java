package com.acme.workflows.invoicing.java;

import com.acme.services.invoice.Invoice;
import com.acme.services.invoice.InvoiceService;
import com.acme.services.metrics.MetricsService;
import com.acme.services.notification.NotificationService;
import com.acme.utils.AbstractWorkflow;
import com.acme.workflows.invoicing.InvoicingWorkflow;
import com.acme.workflows.invoicing.User;
import com.acme.workflows.loyaty.LoyaltyWorkflow;
import com.acme.workflows.payment.PaymentWorkflow;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class InvoicingWorkflowImpl extends AbstractWorkflow implements InvoicingWorkflow {
    // workflow stub that targets itself
    private final InvoicingWorkflow self = getWorkflowById(InvoicingWorkflow.class, getWorkflowId());

    // create stub for MetricsService
    private final MetricsService metricsService = newService(MetricsService.class);

    // create stub for PaymentWorkflow
    private final PaymentWorkflow paymentWorkflow = newWorkflow(PaymentWorkflow.class);

    // create stub for InvoiceService
    private final InvoiceService invoiceService = newService(InvoiceService.class);

    // create stub for NotificationService
    private final NotificationService notificationService = newService(NotificationService.class);

    public void start(User user) {
        // get current date
        LocalDateTime start = inline(LocalDateTime::now);
        // get start of first day of next month
        LocalDateTime end = start.plusMinutes(1);
        // LocalDateTime end = start.withDayOfMonth(1).plusMonths(1).truncatedTo(ChronoUnit.DAYS);
        // wait until then
        timer(Duration.between(start, end)).await();
        // calculate how much the user will pay
        double amount = metricsService.calculateAmount(user, start, end);
        // get payment for the user
        paymentWorkflow.getPayment(user, amount);
        // generate the invoice
        Invoice invoice = invoiceService.create(user, amount, start, end);
        // send the invoice
        notificationService.sendInvoice(user, invoice);
        // loop if the user is still subscribed
        if (metricsService.isSubscribed(user)) {
            dispatchVoid(self::start, user);
        }
    }
}
