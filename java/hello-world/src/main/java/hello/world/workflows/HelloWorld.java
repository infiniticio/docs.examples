package hello.world.workflows;

import io.infinitic.annotations.Name;

@Name(name = "HelloWorkflow")
public interface HelloWorld {
    String greet(String name);
}
