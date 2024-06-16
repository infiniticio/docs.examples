package hello.world.services

@Suppress("unused")
class HelloWorldServiceImpl : HelloWorldService {
    override fun sayHello(name: String): String {
        return "Hello $name"
    }

    override fun addEnthusiasm(str: String): String {
        return "$str!"
    }
}
