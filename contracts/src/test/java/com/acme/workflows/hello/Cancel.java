package com.acme.workflows.hello;

import io.infinitic.clients.InfiniticClient;

import java.io.IOException;

import static com.acme.workflows.hello.Start.helloWorldTag;

public class Cancel {
    public static void main(String[] args) throws IOException {
        try(InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {
            // cancelling the existing HelloWorld instances with the helloWorld tag
            HelloWorkflow instances = client.getWorkflowByTag(HelloWorkflow.class, helloWorldTag);
            client.cancel(instances);
        }
    }
}
