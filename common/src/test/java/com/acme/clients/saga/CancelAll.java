package com.acme.clients.saga;

import com.acme.contracts.workflows.saga.SagaWorkflow;
import io.infinitic.clients.InfiniticClient;

import static com.acme.clients.saga.Start.sagaTag;

public class CancelAll {
    public static void main(String[] args) {
        try(InfiniticClient client = InfiniticClient.fromYamlResource("/infinitic.yml")) {
            // cancelling the existing BookingWorkflow instances with the booking tag
            SagaWorkflow instances = client.getWorkflowByTag(SagaWorkflow.class, sagaTag);
            client.cancel(instances);
        }
    }
}