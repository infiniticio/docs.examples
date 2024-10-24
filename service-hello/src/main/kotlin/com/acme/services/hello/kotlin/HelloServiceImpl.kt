package com.acme.services.hello.kotlin

import com.acme.contracts.services.hello.HelloService
import com.acme.common.AbstractService.log

@Suppress("unused")
class HelloServiceImpl : HelloService {
    override fun sayHello(name: String): String {
        log("sayHello(\"$name\")")

        return "Hello $name"
    }

    override fun addEnthusiasm(str: String): String {
        log("addEnthusiasm(\"$str\")")

        return "$str!"
    }
}
