package com.acme.services.notification.java;

import io.infinitic.workers.InfiniticWorker;

public class Worker {
    public static void main(String[] args) {
        // infinitic.yml resource is from the dependant "contracts" module
        try (InfiniticWorker worker = InfiniticWorker.fromConfigResource("/infinitic.yml", "/notification.yml")) {
            worker.start();
        }
    }
}
