package com.acme.workflows.invoicing;

import com.acme.workflows.loyaty.LoyaltyWorkflow;
import com.acme.workflows.utils.AbstractClient;
import io.infinitic.clients.InfiniticClient;

import java.io.IOException;
import java.util.Objects;

public class Cancel extends AbstractClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        try (InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {

            for (String customId : getCustomIds(args)) {
                // create a stub from InvoicingWorkflow interface, with a customId tag
                InvoicingWorkflow instances = client.getWorkflowByTag(InvoicingWorkflow.class, customId);

                client.cancel(instances);
            }
        }
    }
}

