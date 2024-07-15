package com.acme.workflows.loyalty.java;

import io.infinitic.workers.InfiniticWorker;

public class Worker {
    public static void main(String[] args) {
        try (InfiniticWorker worker = InfiniticWorker.fromConfigResource("/infinitic.yml", "/loyalty.yml")) {
            worker.start();
        }
    }
}
