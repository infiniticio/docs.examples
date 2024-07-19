package com.acme.workflows.booking;

import com.acme.workflows.utils.AbstractClient;
import io.infinitic.clients.InfiniticClient;

import java.io.IOException;

import static com.acme.workflows.booking.Start.bookingTag;

public class CancelAll extends AbstractClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        try (InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {
            // Target the BookingWorkflow instances with this customId tag
            BookingWorkflow instance = client.getWorkflowByTag(BookingWorkflow.class, bookingTag);
            // Cancel those instances
            client.cancel(instance);
        }
    }
}

