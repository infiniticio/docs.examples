package com.acme.clients.invoicing;

import com.acme.common.AbstractClient;
import com.acme.contracts.workflows.invoicing.InvoicingWorkflow;
import io.infinitic.clients.InfiniticClient;

import java.io.IOException;

public class Cancel extends AbstractClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        try (InfiniticClient client = InfiniticClient.fromYamlResource("/infinitic.yml")) {

            for (String customId : getCustomIds(args)) {
                // create a stub from InvoicingWorkflow interface, with a customId tag
                InvoicingWorkflow instances = client.getWorkflowByTag(InvoicingWorkflow.class, customId);

                client.cancel(instances);
            }
        }
    }
}

