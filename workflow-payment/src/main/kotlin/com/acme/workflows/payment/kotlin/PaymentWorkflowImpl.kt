package com.acme.workflows.payment.kotlin

import com.acme.services.notification.BookingRequest
import com.acme.utils.AbstractWorkflow
import com.acme.workflows.invoicing.User
import com.acme.workflows.payment.PaymentWorkflow

class PaymentWorkflowImpl : AbstractWorkflow(), PaymentWorkflow {
    override fun getDeposit(request: BookingRequest) {
        log("Request ${request.id}: Running ${::getDeposit.name}" )
    }

    override fun getPayment(user: User, amount: Double) {
        log("Get payment $amount from User ${user.id}" )
    }
}