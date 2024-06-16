package hello.world;

import io.infinitic.dashboard.InfiniticDashboard;

public class Dashboard {
    public static void main(String[] args) {
        // start server on port defined in infinitic.yml
        InfiniticDashboard.fromConfigResource( "/infinitic.yml").start();
    }
}
