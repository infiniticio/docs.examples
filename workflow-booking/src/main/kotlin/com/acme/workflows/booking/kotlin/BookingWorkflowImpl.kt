package com.acme.workflows.booking.kotlin

import com.acme.services.notification.BookingRequest
import com.acme.services.notification.NotificationService
import com.acme.utils.AbstractWorkflow
import com.acme.workflows.booking.BookingRequestStatus
import com.acme.workflows.booking.BookingWorkflow
import com.acme.workflows.payment.PaymentWorkflow
import io.infinitic.annotations.Ignore
import java.time.Duration
import io.infinitic.workflows.or

class BookingWorkflowImpl: AbstractWorkflow(), BookingWorkflow {

    @Ignore
    private val DAY_IN_SECONDS = 10L // 24 * 3600

    // create stub for HostService
    private val notificationService = newService(NotificationService::class.java)

    // create stub for PaymentWorkflow
    private val paymentWorkflow: PaymentWorkflow = newWorkflow(
        PaymentWorkflow::class.java)

    // create channel for BookingRequestStatus
    private val responseChannel = channel<BookingRequestStatus>()

    override fun getResponseChannel() = responseChannel

    override fun start(request: BookingRequest) {
        // start accepting one signal in the channel
        val deferredResponse = responseChannel.receive(1)

        repeat(3) { attempt ->
            // notify host of traveler request
            dispatch(notificationService::notifyHostOfRequest, request, attempt + 1)
            // start a timer for a day
            val timer = timer(Duration.ofSeconds(DAY_IN_SECONDS))
            // wait for the timer or the signal
            (timer or deferredResponse).await()
            //  exit loop if we received a response from the host
            if (deferredResponse.isCompleted()) return@repeat
        }

        // we did not receive host's response
        if (!deferredResponse.isCompleted()) {
            // notify host of request expiration
            dispatch(notificationService::notifyHostOfRequestExpiration, request)
            // notify traveler of request expiration
            dispatch(notificationService::notifyTravelerOfRequestExpiration, request)
            // workflow stops here
            return
        }

        when(deferredResponse.await()) {
            BookingRequestStatus.ACCEPTED -> bookingAccepted(request)
            BookingRequestStatus.DENIED -> bookingDenied(request)
        }
    }

    private fun bookingAccepted(request: BookingRequest) {
        log("Booking accepted by Host ${request.host}")

        // trigger deposit workflow and wait for it
        paymentWorkflow.getDeposit(request)

        // notify host of the successful payment
        dispatch(notificationService::notifyHostOfPaymentSuccess, request)

        // notify traveler of the successful booking
        dispatch(notificationService::notifyTravelerOfBookingSuccess, request)
    }

    private fun bookingDenied(request: BookingRequest) {
        log("Booking denied by Host ${request.host}")

        // notify traveler of host denial
        dispatch(notificationService::notifyTravelerOfBookingDenial, request)
    }
}