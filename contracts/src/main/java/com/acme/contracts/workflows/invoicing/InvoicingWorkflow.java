package com.acme.contracts.workflows.invoicing;

import io.infinitic.annotations.Name;

@Name(name = "InvoicingWorkflow")
public interface InvoicingWorkflow {
    void start(User user);
}
