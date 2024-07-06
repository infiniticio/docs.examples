package loyalty

import io.infinitic.dashboard.InfiniticDashboard

fun main(args: Array<String>) {
    // get name of config file
    val file = args.getOrNull(0) ?: "/infinitic.yml"
    // start server on port defined in infinitic.yml
    InfiniticDashboard.fromConfigResource(file).start()
}
