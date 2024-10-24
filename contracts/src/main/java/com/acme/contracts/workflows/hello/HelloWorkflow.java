package com.acme.contracts.workflows.hello;

import io.infinitic.annotations.Name;

@Name(name = "HelloWorkflow")
public interface HelloWorkflow {
    String greet(String name);
}
