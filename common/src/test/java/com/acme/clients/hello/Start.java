package com.acme.clients.hello;

import com.acme.common.AbstractClient;
import com.acme.contracts.workflows.hello.HelloWorkflow;
import io.infinitic.clients.InfiniticClient;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Start extends AbstractClient {
    public static final String helloWorldTag = "helloWorld";

    public static void main(String[] args) {
        try(InfiniticClient client = InfiniticClient.fromYamlResource("/infinitic.yml")) {

            long[] ns = getLongs(args);
            long n = ns.length == 0 ? 1 : ns[0];

            // create a stub from HelloWorld interface
            HelloWorkflow helloWorld = client.newWorkflow(HelloWorkflow.class, Set.of(helloWorldTag));

            CompletableFuture<?>[] futures = new CompletableFuture<?>[(int) n];
            for (int i = 0; i < n; i++) {
                // asynchronous dispatch of a workflow
                String strI = String.valueOf(i);
                futures[i] = client.dispatchAsync(helloWorld::greet, strI)
                        .thenApply(deferred -> printDispatched("Workflow " + strI, deferred.getId()))
                        .exceptionally(error -> printError(strI, error));
            }
            CompletableFuture.allOf(futures).join();
        }
    }
}
