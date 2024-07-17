package com.acme.workflows.hello.kotlin

import com.acme.services.hello.HelloService
import com.acme.utils.AbstractWorkflow
import com.acme.workflows.hello.HelloWorkflow

class HelloWorkflowImpl : AbstractWorkflow(), HelloWorkflow {

    private val helloService = newService(HelloService::class.java)

    override fun greet(name: String): String {
        val str = helloService.sayHello(name)

        val greeting = helloService.addEnthusiasm(str)

        log(greeting)

        return greeting
    }
}
