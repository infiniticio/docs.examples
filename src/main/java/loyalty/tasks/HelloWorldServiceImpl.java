package loyalty.tasks;

import io.infinitic.tasks.Task;

public class HelloWorldServiceImpl extends Task implements HelloWorldService {
    @Override
    public String sayHello(String name) {

        return "Hello " + ((name == null) ? "World" : name);
    }

    @Override
    public String addEnthusiasm(String str) {

        return str + "!";
    }
}