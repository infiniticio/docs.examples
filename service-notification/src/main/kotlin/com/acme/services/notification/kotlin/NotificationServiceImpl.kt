package com.acme.services.notification.kotlin

import com.acme.services.notification.BookingRequest
import com.acme.services.notification.NotificationService
import com.acme.utils.AbstractService.log


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
}
