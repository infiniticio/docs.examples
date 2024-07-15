package com.acme.workflows.saga.java;

import io.infinitic.workers.InfiniticWorker;

public class Worker {
    public static void main(String[] args) {
        try (InfiniticWorker worker = InfiniticWorker.fromConfigResource("/infinitic.yml", "/saga.yml")) {
            worker.start();
        }
    }
}
