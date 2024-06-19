package example.booking;

import io.infinitic.workers.InfiniticWorker;

public class Worker {
    public static void main(String[] args) {
        String file = args.length > 0 ? args[0] : "/configs/all.yml";

        try (InfiniticWorker worker = InfiniticWorker.fromConfigResource(file, "/configs/infinitic.yml")) {
            worker.start();
        }
    }
}
