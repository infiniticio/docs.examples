package com.acme.workflows.booking;

import com.acme.workflows.utils.AbstractClient;
import io.infinitic.clients.InfiniticClient;

public class Send extends AbstractClient {
    public static void main(String[] args) throws RuntimeException, InterruptedException {
        try(InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {
            // Retrieve the provided status
            BookingRequestStatus requestStatus = BookingRequestStatus.getFromString((args.length == 0) ? "First arg" : args[0]);
            // Retrieve the provide id
            String customId = getCustomId((args.length > 1) ? args[1] : null);
            // Target the BookingWorkflow instance with this customId tag
            BookingWorkflow instance = client.getWorkflowByTag(BookingWorkflow.class, customId);
            // Send the status to this instance through the responseChannel
            client.dispatchVoidAsync(instance.getResponseChannel()::send, requestStatus)
                    .thenApply(deferred -> printDispatched("Event " + requestStatus.toString(), customId))
                    .exceptionally(error -> printError(requestStatus.toString(), error))
                    .join();
        }
    }
}
