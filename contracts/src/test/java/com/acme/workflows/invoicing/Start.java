package com.acme.workflows.invoicing;

import com.acme.workflows.hello.HelloWorkflow;
import com.acme.workflows.loyaty.LoyaltyWorkflow;
import com.acme.workflows.utils.AbstractClient;
import io.infinitic.clients.InfiniticClient;

import java.io.IOException;
import java.util.Set;

public class Start extends AbstractClient {
    public static final String invoicingTag = "invoicing";

    public static void main(String[] args) throws InterruptedException, IOException {

        try (InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {

            long[] ns = getLongs(args);
            long n = ns.length == 0 ? 1 : ns[0];

            for (int i = 0; i < n; i++) {
                String userId = "user" + i;
                String customId = getCustomId(userId);
                // create a stub from InvoicingWorkflow interface, with a customId tag
                InvoicingWorkflow invoicing = client.newWorkflow(InvoicingWorkflow.class, Set.of(invoicingTag, customId));

                client.dispatchVoidAsync(invoicing::start, new User(userId))
                        .thenApply(deferred -> printDispatched(customId, deferred.getId()))
                        .exceptionally(error -> printError(customId, error))
                        .join();
            }
        }
    }
}
