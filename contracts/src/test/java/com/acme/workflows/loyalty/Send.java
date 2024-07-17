package com.acme.workflows.loyalty;

import com.acme.workflows.utils.AbstractClient;
import com.acme.workflows.loyaty.BonusEvent;
import com.acme.workflows.loyaty.LoyaltyWorkflow;
import io.infinitic.clients.InfiniticClient;

public class Send extends AbstractClient {
    public static void main(String[] args) throws RuntimeException, InterruptedException {
        try(InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {

            BonusEvent bonusEvent = BonusEvent.getFromString((args.length == 0) ? "First arg" : args[0]);
            String customId = AbstractClient.getCustomId((args.length > 1) ? args[1] : null);

            LoyaltyWorkflow instance = client.getWorkflowByTag(LoyaltyWorkflow.class, customId);
            client.dispatchVoidAsync(instance::addBonus, bonusEvent)
                    .thenApply(deferred -> AbstractClient.printDispatched("Event " + bonusEvent.toString(), customId))
                    .exceptionally(error -> AbstractClient.printError(bonusEvent.toString(), error))
                    .join();
        }
    }
}

