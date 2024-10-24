package com.acme.clients.booking;

import com.acme.common.AbstractClient;
import com.acme.contracts.workflows.booking.BookingWorkflow;
import io.infinitic.clients.InfiniticClient;

import java.io.IOException;

import static com.acme.clients.booking.Start.bookingTag;

public class CancelAll extends AbstractClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        try (InfiniticClient client = InfiniticClient.fromYamlResource("/infinitic.yml")) {
            // Target the BookingWorkflow instances with this customId tag
            BookingWorkflow instance = client.getWorkflowByTag(BookingWorkflow.class, bookingTag);
            // Cancel those instances
            client.cancel(instance);
        }
    }
}

