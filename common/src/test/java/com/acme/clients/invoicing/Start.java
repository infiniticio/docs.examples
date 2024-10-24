package com.acme.clients.invoicing;

import com.acme.common.AbstractClient;
import com.acme.contracts.workflows.invoicing.InvoicingWorkflow;
import com.acme.contracts.workflows.invoicing.User;
import io.infinitic.clients.InfiniticClient;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Start extends AbstractClient {
    public static final String invoicingTag = "invoicing";

    public static void main(String[] args) throws InterruptedException, IOException {

        try (InfiniticClient client = InfiniticClient.fromYamlResource("/infinitic.yml")) {

            long[] ns = getLongs(args);
            long n = ns.length == 0 ? 1 : ns[0];

            CompletableFuture<?>[] futures = new CompletableFuture<?>[(int) n];
            for (int i = 0; i < n; i++) {
                String userId = "user" + i;
                String customId = getCustomId(userId);
                // create a stub from InvoicingWorkflow interface, with a customId tag
                InvoicingWorkflow invoicing = client.newWorkflow(InvoicingWorkflow.class, Set.of(invoicingTag, customId));

                futures[i] = client.dispatchVoidAsync(invoicing::start, new User(userId))
                        .thenApply(deferred -> printDispatched(customId, deferred.getId()))
                        .exceptionally(error -> printError(customId, error));
            }
            CompletableFuture.allOf(futures).join();
        }
    }
}
