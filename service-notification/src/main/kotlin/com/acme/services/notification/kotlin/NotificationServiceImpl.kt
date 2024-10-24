package com.acme.services.notification.kotlin

import com.acme.contracts.services.invoice.Invoice
import com.acme.contracts.services.notification.BookingRequest
import com.acme.contracts.services.notification.NotificationService
import com.acme.common.AbstractService.log
import com.acme.contracts.workflows.invoicing.User


class NotificationServiceImpl: NotificationService {
    override fun notifyHostOfRequest(request: BookingRequest, attempt: Int) {
        log("Request ${request.id}: Notifying Host ${request.host} of a new request from ${request.traveler} (attempt $attempt)")
    }

    override fun notifyHostOfRequestExpiration(request: BookingRequest) {
        log("Request ${request.id}: Notifying Host ${request.host} of the expiration the request")
    }

    override fun notifyTravelerOfRequestExpiration(request: BookingRequest) {
        log("Request ${request.id}: Notifying Traveler ${request.traveler} of the expiration her request")
    }

    override fun notifyHostOfPaymentSuccess(request: BookingRequest) {
            log("Request ${request.id}: Notifying Host ${request.host} of a successful payment")
    }

    override fun notifyTravelerOfBookingSuccess(request: BookingRequest) {
        log("Request ${request.id}: Notifying Traveler ${request.traveler} of the success of her booking")
    }

    override fun notifyTravelerOfBookingDenial(request: BookingRequest) {
        log("Request ${request.id}: Notifying Traveler ${request.traveler} of the denial of her booking")
    }

    override fun sendInvoice(user: User, invoice: Invoice) {
        log("Send Invoice ${invoice.id} to user ${user.id}")
    }
}
