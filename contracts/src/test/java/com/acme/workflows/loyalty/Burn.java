package com.acme.workflows.loyalty;

import com.acme.workflows.loyaty.LoyaltyWorkflow;
import com.acme.workflows.utils.AbstractClient;
import io.infinitic.clients.InfiniticClient;

import java.util.Arrays;

public class Burn extends AbstractClient {
    public static void main(String[] args) throws RuntimeException, InterruptedException {
        try(InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {

            long[] amounts = getLongs(args);
            if (amounts.length == 0) throw new IllegalArgumentException("No amount provided");
            if (amounts.length > 1) throw new IllegalArgumentException("More than one amount provided");
            long amount = amounts[0];

            for(String customId:  getCustomIds(excludeValue(String.valueOf(amount),args))) {
                    LoyaltyWorkflow instance = client.getWorkflowByTag(LoyaltyWorkflow.class, customId);
                    // Send the status to this instance through the responseChannel
                    client.dispatchVoidAsync(instance::burn, amount)
                            .thenApply(deferred -> printDispatched("burn(" + amount+")", customId))
                            .exceptionally(error -> printError("burn(" + amount+") on "+ customId, error))
                            .join();
            }
        }
    }
}
