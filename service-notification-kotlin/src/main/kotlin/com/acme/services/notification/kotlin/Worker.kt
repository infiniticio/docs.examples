package com.acme.services.notification.kotlin

import io.infinitic.workers.InfiniticWorker

fun main() {
    // infinitic.yml resource is from the dependant "contracts" module
    InfiniticWorker.fromConfigResource("/infinitic.yml", "/notification.yml").use { worker ->
        worker.start()
    }
}
