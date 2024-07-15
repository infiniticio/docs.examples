package com.acme.services.hello.kotlin

import io.infinitic.workers.InfiniticWorker

fun main() {
    // infinitic.yml resource is from the dependant "contracts" module
    InfiniticWorker.fromConfigResource("/infinitic.yml", "/hello.yml").use { worker ->
        worker.start()
    }
}
