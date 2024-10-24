package com.acme.services.hello.java;

import com.acme.contracts.services.hello.HelloService;
import com.acme.common.AbstractService;

public class HelloServiceImpl extends AbstractService implements HelloService {
    @Override
    public String sayHello(String name) {
        log("sayHello(\"" + name + "\")");

        return "Hello " + ((name == null) ? "World" : name);
    }

    @Override
    public String addEnthusiasm(String str) {
        log("addEnthusiasm(\"" + str + "\")");

        return str + "!";
    }
}