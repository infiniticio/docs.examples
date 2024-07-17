package com.acme.workflows.booking;

import com.acme.workflows.utils.AbstractClient;
import io.infinitic.clients.InfiniticClient;

import java.io.IOException;

public class Cancel extends AbstractClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        try (InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {
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

