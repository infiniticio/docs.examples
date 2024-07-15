package com.acme.workflows.hello;

import io.infinitic.annotations.Name;

@Name(name = "HelloWorkflow")
public interface HelloWorkflow {
    String greet(String name);
}
