package hello.world.services;

import io.infinitic.annotations.Name;

@Name(name = "HelloService")
public interface HelloWorldService {

    String sayHello(String name);

    String addEnthusiasm(String str);
}