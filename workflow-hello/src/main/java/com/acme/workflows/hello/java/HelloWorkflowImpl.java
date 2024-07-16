package com.acme.workflows.hello.java;

import com.acme.services.hello.HelloService;
import com.acme.utils.AbstractWorkflow;
import com.acme.workflows.hello.HelloWorkflow;

public class HelloWorkflowImpl extends AbstractWorkflow implements HelloWorkflow {
    private final HelloService helloService = newService(HelloService.class);

    @Override public String greet(String name) {
        String str = helloService.sayHello(name);

        String greeting = helloService.addEnthusiasm(str);

        log(greeting);

        return greeting;
    }
}
