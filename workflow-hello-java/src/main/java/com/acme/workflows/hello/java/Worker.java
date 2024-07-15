package com.acme.workflows.hello.java;

import io.infinitic.workers.InfiniticWorker;

public class Worker {
    public static void main(String[] args) {
        try (InfiniticWorker worker = InfiniticWorker.fromConfigResource("/infinitic.yml", "/hello.yml")) {
            worker.start();
        }
    }
}
