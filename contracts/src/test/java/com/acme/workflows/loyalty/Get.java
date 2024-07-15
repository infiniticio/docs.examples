package com.acme.workflows.loyalty;

import com.acme.workflows.utils.AbstractClient;
import com.acme.workflows.loyaty.LoyaltyWorkflow;
import io.infinitic.clients.InfiniticClient;

public class Get extends AbstractClient {
    public static void main(String[] args) throws RuntimeException, InterruptedException {
        try(InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {
            for (String customId : AbstractClient.getCustomIds(args)) {
                LoyaltyWorkflow instance = client.getWorkflowByTag(LoyaltyWorkflow.class, customId);
                for (String id : client.getIds(instance)) {
                    System.out.println("Workflow " + id + " (" + customId + ") - points " + client.getWorkflowById(LoyaltyWorkflow.class, id).getPoints());
                }
            }
        }
    }
}

