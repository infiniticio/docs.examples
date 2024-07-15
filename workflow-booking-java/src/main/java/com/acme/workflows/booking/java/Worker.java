package com.acme.workflows.booking.java;

import io.infinitic.workers.InfiniticWorker;

import java.net.URL;

public class Worker {
    public static void main(String[] args) {
        try (InfiniticWorker worker = InfiniticWorker.fromConfigResource("/infinitic.yml", "/booking.yml")) {
            worker.start();
        }
    }
}
