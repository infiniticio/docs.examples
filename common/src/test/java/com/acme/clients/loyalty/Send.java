package com.acme.clients.loyalty;

import com.acme.common.AbstractClient;
import com.acme.contracts.workflows.loyaty.BonusEvent;
import com.acme.contracts.workflows.loyaty.LoyaltyWorkflow;
import io.infinitic.clients.InfiniticClient;

public class Send extends AbstractClient {
    public static void main(String[] args) throws RuntimeException, InterruptedException {
        try(InfiniticClient client = InfiniticClient.fromYamlResource("/infinitic.yml")) {

            BonusEvent[] bonusEvents = getEnums(BonusEvent.class, args);
            String[] customIds = getCustomIds(excludeEnums(BonusEvent.class, args));

            for (BonusEvent bonusEvent : bonusEvents) {
                for (String customId : customIds) {
                    LoyaltyWorkflow instance = client.getWorkflowByTag(LoyaltyWorkflow.class, customId);
                    client.dispatchVoidAsync(instance::addBonus, bonusEvent)
                            .thenApply(deferred -> printDispatched("Event " + bonusEvent.toString(), customId))
                            .exceptionally(error -> printError(bonusEvent.toString(), error))
                            .join();
                }
            }
        }
    }
}
