package com.acme.workflows.hello;

import com.acme.workflows.utils.AbstractClient;
import io.infinitic.clients.InfiniticClient;

import java.util.Set;

public class Start extends AbstractClient {
    public static final String helloWorldTag = "helloWorld";

    public static void main(String[] args) {
        try(InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {

            // create a stub from HelloWorld interface
            HelloWorkflow helloWorld = client.newWorkflow(HelloWorkflow.class, Set.of(helloWorldTag));

            int i = 0;
            while (i < getLong(args)) {
                // asynchronous dispatch of a workflow
                String strI = String.valueOf(i);
                client.dispatchAsync(helloWorld::greet, strI)
                        .thenApply(deferred -> printDispatched("Workflow " + strI, deferred.getId()))
                        .exceptionally(error -> printError(strI, error));
                i++;
            }
        }
    }
}
