package com.acme.services.carRental.kotlin

import io.infinitic.workers.InfiniticWorker

fun main() {
    // infinitic.yml resource is from the dependant "contracts" module
    InfiniticWorker.fromConfigResource("/infinitic.yml", "/carRental.yml").use { worker ->
        worker.start()
    }
}
