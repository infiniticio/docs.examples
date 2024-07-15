package com.acme.workflows.loyalty;

import com.acme.workflows.utils.AbstractClient;
import com.acme.workflows.loyaty.LoyaltyWorkflow;
import io.infinitic.clients.InfiniticClient;

import java.io.IOException;

public class Cancel extends AbstractClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        try (InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {

            for (String customId : AbstractClient.getCustomIds(args)) {
                // create a stub from Loyalty interface, with a customId tag
                LoyaltyWorkflow instances = client.getWorkflowByTag(LoyaltyWorkflow.class, customId);

                client.cancel(instances);
            }
        }
    }
}

