package loyalty

import io.infinitic.factory.InfiniticWorkerFactory

fun main(args: Array<String>) {
    InfiniticWorkerFactory.fromConfigFile("infinitic.yml").use { worker ->
        worker.start()
    }
}
