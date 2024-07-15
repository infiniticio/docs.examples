package com.acme.workflows.payment.kotlin

import io.infinitic.workers.InfiniticWorker

fun main() {
    // infinitic.yml resource is from the dependant "contracts" module
    InfiniticWorker.fromConfigResource("/infinitic.yml", "/payment.yml").use { worker ->
        worker.start()
    }
}
