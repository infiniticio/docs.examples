package com.acme.workflows.loyalty;

import com.acme.workflows.utils.AbstractClient;
import com.acme.workflows.loyaty.BonusEvent;
import com.acme.workflows.loyaty.LoyaltyWorkflow;
import io.infinitic.clients.InfiniticClient;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Send extends AbstractClient {
    public static void main(String[] args) throws RuntimeException, InterruptedException {
        try(InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {

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
