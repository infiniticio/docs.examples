package com.acme.services.flightBooking.kotlin

import io.infinitic.workers.InfiniticWorker

fun main() {
    // infinitic.yml resource is from the dependant "contracts" module
    InfiniticWorker.fromConfigResource("/infinitic.yml", "/flightBooking.yml").use { worker ->
        worker.start()
    }
}
