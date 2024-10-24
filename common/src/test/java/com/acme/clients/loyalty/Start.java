package com.acme.clients.loyalty;

import com.acme.common.AbstractClient;
import com.acme.contracts.workflows.loyaty.LoyaltyWorkflow;
import io.infinitic.clients.InfiniticClient;

import java.io.IOException;
import java.util.Set;

public class Start extends AbstractClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        try (InfiniticClient client = InfiniticClient.fromYamlResource("/infinitic.yml")) {

            for (String customId : getCustomIds(args)) {
                // create a stub from Loyalty interface, with a customId tag
                LoyaltyWorkflow loyalty = client.newWorkflow(LoyaltyWorkflow.class, Set.of(customId));

                client.dispatchVoidAsync(loyalty::start)
                        .thenApply(deferred -> printDispatched(customId, deferred.getId()))
                        .exceptionally(error -> printError(customId, error))
                        .join();
            }
        }
    }
}
