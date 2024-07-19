package com.acme.workflows.invoicing;

import com.acme.workflows.utils.AbstractClient;
import io.infinitic.clients.InfiniticClient;

import java.io.IOException;

import static com.acme.workflows.invoicing.Start.invoicingTag;

public class CancelAll extends AbstractClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        try (InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {
                // create a stub from InvoicingWorkflow interface, with the invoicing tag
                InvoicingWorkflow instances = client.getWorkflowByTag(InvoicingWorkflow.class, invoicingTag);

                client.cancel(instances);
        }
    }
}
