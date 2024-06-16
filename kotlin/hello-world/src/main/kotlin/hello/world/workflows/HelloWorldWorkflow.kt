package hello.world.workflows

import io.infinitic.annotations.Name

@Name("HelloWorkflow")
interface HelloWorldWorkflow {
    fun greet(name: String): String
}
