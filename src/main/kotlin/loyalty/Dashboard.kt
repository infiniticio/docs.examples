package loyalty

import io.infinitic.dashboard.DashboardServer

fun main(args: Array<String>) {
    // get name of config file
    val file = args.getOrNull(0) ?: "infinitic.yml"
    // start server
    DashboardServer.fromConfigFile(file).start()
}
