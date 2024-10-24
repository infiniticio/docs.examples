package com.acme.clients.invoicing;

import com.acme.common.AbstractClient;
import com.acme.contracts.workflows.invoicing.InvoicingWorkflow;
import io.infinitic.clients.InfiniticClient;

import java.io.IOException;

import static com.acme.clients.invoicing.Start.invoicingTag;

public class CancelAll extends AbstractClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        try (InfiniticClient client = InfiniticClient.fromYamlResource("/infinitic.yml")) {
                // create a stub from InvoicingWorkflow interface, with the invoicing tag
                InvoicingWorkflow instances = client.getWorkflowByTag(InvoicingWorkflow.class, invoicingTag);

                client.cancel(instances);
        }
    }
}
