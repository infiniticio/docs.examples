package hello.world.services;

public class HelloWorldServiceImpl implements HelloWorldService {
    @Override
    public String sayHello(String name) {

        return "Hello " + ((name == null) ? "World" : name);
    }

    @Override
    public String addEnthusiasm(String str) {

        return str + "!";
    }
}