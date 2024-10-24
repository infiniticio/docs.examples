package com.acme.workflows.payment.kotlin

import com.acme.contracts.services.notification.BookingRequest
import com.acme.common.AbstractWorkflow
import com.acme.contracts.workflows.invoicing.User
import com.acme.contracts.workflows.payment.PaymentWorkflow

class PaymentWorkflowImpl : AbstractWorkflow(),
    PaymentWorkflow {
    override fun getDeposit(request: BookingRequest) {
        log("Request ${request.id}: Running ${::getDeposit.name}" )
    }

    override fun getPayment(user: User, amount: Double) {
        log("Get payment $amount from User ${user.id}" )
    }
}