package com.acme.workflows.booking.java;

import com.acme.services.notification.BookingRequest;
import com.acme.services.notification.NotificationService;
import com.acme.utils.AbstractWorkflow;
import com.acme.workflows.booking.BookingRequestStatus;
import com.acme.workflows.booking.BookingWorkflow;
import com.acme.workflows.payment.PaymentWorkflow;
import io.infinitic.annotations.Ignore;
import io.infinitic.workflows.Channel;
import io.infinitic.workflows.Deferred;

import java.time.Duration;
import java.time.Instant;

import static io.infinitic.workflows.DeferredKt.or;

public class BookingWorkflowImpl extends AbstractWorkflow implements BookingWorkflow {
    @Ignore
    private final long DAY_IN_SECONDS = 10L; // 24 * 3600

    // create channel for BookingRequestStatus
    private final Channel<BookingRequestStatus> responseChannel = channel();

    // create stub for HostService
    private final NotificationService notificationService = newService(NotificationService.class);

    // create stub for PaymentWorkflow
    private final PaymentWorkflow paymentWorkflow = newWorkflow(PaymentWorkflow.class);

    @Override
    public Channel<BookingRequestStatus> getResponseChannel() {
        return responseChannel;
    }

    @Override
    public void start(BookingRequest request) {
        // start accepting one signal in the channel
        Deferred<BookingRequestStatus> deferredResponse = responseChannel.receive(1);

        for (int attempt = 1; attempt <= 3; attempt++) {
            // notify host of traveler request
            dispatchVoid(notificationService::notifyHostOfRequest, request, attempt);

            // start a timer for a day
            Deferred<Instant> timer = timer(Duration.ofSeconds(DAY_IN_SECONDS));

            // Checks the completion of one of them
            or(timer, deferredResponse).await();

            // exit loop if we received a response from the host
            if (deferredResponse.isCompleted()) break;
        }

        // we did not receive host's response
        if (!deferredResponse.isCompleted()) {
            // notify host of request expiration
            dispatchVoid(notificationService::notifyHostOfRequestExpiration, request);
            // notify traveler of request expiration
            dispatchVoid(notificationService::notifyTravelerOfRequestExpiration, request);
            // workflow stops here
            return;
        }

        BookingRequestStatus response = deferredResponse.await();
        switch (response) {
            case ACCEPTED:
                bookingAccepted(request);
                break;
            case DENIED:
                bookingDenied(request);
                break;
        }
    }

    private void bookingAccepted(BookingRequest request) {
        log("Booking accepted by Host " + request.getHost());

        // trigger deposit workflow and wait for it
        paymentWorkflow.getDeposit(request);

        // notify host of the successful payment
        dispatchVoid(notificationService::notifyHostOfPaymentSuccess, request);

        // notify traveler of the successful booking
        dispatchVoid(notificationService::notifyTravelerOfBookingSuccess, request);
    }

    private void bookingDenied(BookingRequest request) {
        log("Booking denied by Host " + request.getHost());

        // notify traveler of host denial
        dispatchVoid(notificationService::notifyTravelerOfBookingDenial, request);
    }
}
