package com.acme.services.hotelBooking.kotlin

import io.infinitic.workers.InfiniticWorker

fun main() {
    // infinitic.yml resource is from the dependant "contracts" module
    InfiniticWorker.fromConfigResource("/infinitic.yml", "/hotelBooking.yml").use { worker ->
        worker.start()
    }
}
