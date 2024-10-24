package com.acme.clients.booking;

import com.acme.common.AbstractClient;
import com.acme.contracts.workflows.booking.BookingWorkflow;
import io.infinitic.clients.InfiniticClient;

import java.io.IOException;

public class Cancel extends AbstractClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        try (InfiniticClient client = InfiniticClient.fromYamlResource("/infinitic.yml")) {
            // For all provided ids
            for (String customId : getCustomIds(args)) {
                // Target the BookingWorkflow instances with this customId tag
                BookingWorkflow instance = client.getWorkflowByTag(BookingWorkflow.class, customId);
                // Cancel this instance
                client.cancel(instance);
            }
        }
    }
}

