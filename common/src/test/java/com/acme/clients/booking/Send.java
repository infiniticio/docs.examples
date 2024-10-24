package com.acme.clients.booking;

import com.acme.common.AbstractClient;
import com.acme.contracts.workflows.booking.BookingRequestStatus;
import com.acme.contracts.workflows.booking.BookingWorkflow;
import io.infinitic.clients.InfiniticClient;

public class Send extends AbstractClient {
    public static void main(String[] args) throws RuntimeException, InterruptedException {
        try(InfiniticClient client = InfiniticClient.fromYamlResource("/infinitic.yml")) {
            BookingRequestStatus[] statuses = getEnums(BookingRequestStatus.class, args);
            if (statuses.length == 0) throw new IllegalArgumentException("No BookingRequestStatus provided");
            if (statuses.length > 1) throw new IllegalArgumentException("More than one BookingRequestStatus provided");
            BookingRequestStatus status = statuses[0];

            for (String customId : getCustomIds(excludeEnums(BookingRequestStatus.class, args))) {
                // Target the BookingWorkflow instance with this customId tag
                BookingWorkflow instance = client.getWorkflowByTag(BookingWorkflow.class, customId);
                // Send the status to this instance through the responseChannel
                client.dispatchVoidAsync(instance.getResponseChannel()::send, status)
                        .thenApply(deferred -> printDispatched("Event " + status.toString(), customId))
                        .exceptionally(error -> printError(status.toString(), error))
                        .join();
            }
        }
    }
}
