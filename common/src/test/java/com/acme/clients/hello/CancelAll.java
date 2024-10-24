package com.acme.clients.hello;

import com.acme.contracts.workflows.hello.HelloWorkflow;
import io.infinitic.clients.InfiniticClient;

import java.io.IOException;

import static com.acme.clients.hello.Start.helloWorldTag;

public class CancelAll {
    public static void main(String[] args) throws IOException {
        try(InfiniticClient client = InfiniticClient.fromYamlResource("/infinitic.yml")) {
            // cancelling the existing HelloWorld instances with the helloWorld tag
            HelloWorkflow instances = client.getWorkflowByTag(HelloWorkflow.class, helloWorldTag);
            client.cancel(instances);
        }
    }
}
