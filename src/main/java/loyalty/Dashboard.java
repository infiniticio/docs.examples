package loyalty;

import io.infinitic.dashboard.DashboardServer;

public class Dashboard {
    public static void main(String[] args) {
        String file;
        // get name of config file
        if (args.length > 0) {
            file = args[0];
        } else {
            file = "infinitic.yml";
        }
        // start server
        DashboardServer.fromConfigFile(file).start();
    }
}
