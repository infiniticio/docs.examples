package com.acme.workflows.payment.kotlin

import com.acme.services.notification.BookingRequest
import com.acme.utils.AbstractWorkflow
import com.acme.workflows.payment.PaymentWorkflow

class PaymentWorkflowImpl : AbstractWorkflow(), PaymentWorkflow {
    override fun getDeposit(request: BookingRequest) {
        log("Request ${request.id}: Running ${::getDeposit.name}" )
    }
}