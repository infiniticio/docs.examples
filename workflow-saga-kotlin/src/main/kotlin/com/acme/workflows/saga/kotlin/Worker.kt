package com.acme.workflows.saga.kotlin

import io.infinitic.workers.InfiniticWorker

fun main() {
    // infinitic.yml resource is from the dependant "contracts" module
    InfiniticWorker.fromConfigResource("/infinitic.yml", "/saga.yml").use { worker ->
        worker.start()
    }
}
