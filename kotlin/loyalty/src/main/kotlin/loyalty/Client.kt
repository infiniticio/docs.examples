package loyalty

import io.infinitic.clients.InfiniticClient
import loyalty.workflows.BonusEvent
import loyalty.workflows.Loyalty

fun main() {
    InfiniticClient.fromConfigResource("/infinitic.yml").use { client ->
        // create a stub from HelloWorld interface
        val loyalty = client.newWorkflow(Loyalty::class.java, tags = setOf("<userId>"))

        // asynchronous dispatch of a workflow
        val deferred = client.dispatch(loyalty::start)
        println("Workflow " + Loyalty::class.java.name + " " + deferred.id + " dispatched!")

        // get a reference to this workflow
        val w = client.getWorkflowById(Loyalty::class.java, deferred.id)

        Thread.sleep(5000)
        println("Points: " + w.points)

        Thread.sleep(5000)
        client.dispatch(w::addBonus, BonusEvent.REGISTRATION_COMPLETED)
        println("bonus!")

        Thread.sleep(5000)
        println("Points: " + w.points)

        Thread.sleep(5000)
        client.dispatch(w::addBonus, BonusEvent.ORDER_COMPLETED)
        println("bonus!")

        Thread.sleep(5000)
        println("Points: " + w.points)

        Thread.sleep(5000)
        client.cancel(w)
        println("Workflow canceled")
    }
}
