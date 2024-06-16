package hello.world


import io.infinitic.dashboard.InfiniticDashboard

fun main() {
    // start server on port defined in infinitic.yml
    InfiniticDashboard.fromConfigResource("/infinitic.yml").start()
}
