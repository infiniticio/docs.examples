package com.acme.workflows.saga;

import io.infinitic.clients.InfiniticClient;

import static com.acme.workflows.saga.Start.sagaTag;

public class CancelAll {
    public static void main(String[] args) {
        try(InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {
            // cancelling the existing BookingWorkflow instances with the booking tag
            SagaWorkflow instances = client.getWorkflowByTag(SagaWorkflow.class, sagaTag);
            client.cancel(instances);
        }
    }
}