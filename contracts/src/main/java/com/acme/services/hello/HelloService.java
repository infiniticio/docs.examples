package com.acme.services.hello;

import io.infinitic.annotations.Name;

@Name(name = "HelloService")
public interface HelloService {

    String sayHello(String name);

    String addEnthusiasm(String str);
}