package hello.world.services

import io.infinitic.annotations.Name

@Name("HelloService")
interface HelloWorldService {

    fun sayHello(name: String): String

    fun addEnthusiasm(str: String): String
}