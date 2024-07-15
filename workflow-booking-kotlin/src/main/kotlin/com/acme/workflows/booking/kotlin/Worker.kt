package com.acme.workflows.booking.kotlin

import io.infinitic.workers.InfiniticWorker

fun main() {
    // infinitic.yml resource is from the dependant "contracts" module
    InfiniticWorker.fromConfigResource("/infinitic.yml", "/booking.yml").use { worker ->
        worker.start()
    }
}
