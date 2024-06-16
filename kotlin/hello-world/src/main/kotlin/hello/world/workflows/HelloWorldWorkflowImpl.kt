package hello.world.workflows

import hello.world.services.HelloWorldService
import io.infinitic.workflows.Workflow

class HelloWorldWorkflowImpl : Workflow(), HelloWorldWorkflow {

    private val helloWorldService = newService(HelloWorldService::class.java)

    override fun greet(name: String): String {
        val str = helloWorldService.sayHello(name)

        val greeting = helloWorldService.addEnthusiasm(str)

        inline { println(greeting) }

        return greeting
    }
}
