package hello.world

import hello.world.workflows.HelloWorldWorkflow
import io.infinitic.clients.InfiniticClient

fun main() {
   InfiniticClient.fromConfigResource("/infinitic.yml").use { client ->
       // create a stub from HelloWorld interface
       val helloWorldWorkflow = client.newWorkflow(HelloWorldWorkflow::class.java, tags = setOf("foo", "bar"))

       // dispatch workflows
       repeat(300) {
            // dispatch workflow
            client.dispatchAsync(helloWorldWorkflow::greet, "$it")
                .thenApply { deferred -> println("Workflow ${deferred.id} ($it) dispatched!") }
                .exceptionally { error -> println("Failed to dispatch ($it): $error") }
        }
    }
}
