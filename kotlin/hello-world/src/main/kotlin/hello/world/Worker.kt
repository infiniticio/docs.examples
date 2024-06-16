package hello.world

import io.infinitic.workers.InfiniticWorker

fun main() {
    InfiniticWorker.fromConfigResource("/infinitic.yml").use { worker ->
        worker.start()
    }
}
