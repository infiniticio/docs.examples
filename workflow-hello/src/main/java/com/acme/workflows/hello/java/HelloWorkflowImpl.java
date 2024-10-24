package com.acme.workflows.hello.java;

import com.acme.contracts.services.hello.HelloService;
import com.acme.common.AbstractWorkflow;
import com.acme.contracts.workflows.hello.HelloWorkflow;

public class HelloWorkflowImpl extends AbstractWorkflow implements HelloWorkflow {
    private final HelloService helloService = newService(HelloService.class);

    @Override public String greet(String name) {
        String str = helloService.sayHello(name);

        String greeting = helloService.addEnthusiasm(str);

        log(greeting);

        return greeting;
    }
}
