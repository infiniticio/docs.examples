package com.acme.clients.loyalty;

import com.acme.common.AbstractClient;
import com.acme.contracts.workflows.loyaty.LoyaltyWorkflow;
import io.infinitic.clients.InfiniticClient;

public class Get extends AbstractClient {
    public static void main(String[] args) throws RuntimeException, InterruptedException {
        try(InfiniticClient client = InfiniticClient.fromYamlResource("/infinitic.yml")) {
            for (String customId : getCustomIds(args)) {
                LoyaltyWorkflow instance = client.getWorkflowByTag(LoyaltyWorkflow.class, customId);
                for (String id : client.getIds(instance)) {
                    System.out.println("Workflow " + id + " (" + customId + ") - points " + client.getWorkflowById(LoyaltyWorkflow.class, id).getPoints());
                }
            }
        }
    }
}
