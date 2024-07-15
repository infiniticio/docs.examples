package com.acme.workflows.payment.java;

import io.infinitic.workers.InfiniticWorker;

public class Worker {
    public static void main(String[] args) {
        try (InfiniticWorker worker = InfiniticWorker.fromConfigResource("/infinitic.yml", "/payment.yml")) {
            worker.start();
        }
    }
}
