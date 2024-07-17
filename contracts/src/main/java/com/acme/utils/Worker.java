package com.acme.utils;

import io.infinitic.workers.InfiniticWorker;

public class Worker {
    public static void main(String[] args) {
        if (args.length == 0) throw new RuntimeException("Provide config resource as first argument");

        String file = "/" + args[0] + ".yml";

        // infinitic.yml resource is from the dependant "contracts" module
        try (InfiniticWorker worker = InfiniticWorker.fromConfigResource("/infinitic.yml", file)) {
            worker.start();
        }
    }
}
