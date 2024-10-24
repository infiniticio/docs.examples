package com.acme.contracts.workflows.booking;


import com.acme.contracts.services.notification.BookingRequest;
import io.infinitic.annotations.Name;
import io.infinitic.workflows.SendChannel;

@Name(name = "BookingWorkflow")
public interface BookingWorkflow {
    SendChannel<BookingRequestStatus> getResponseChannel();

    void start(BookingRequest request);
}