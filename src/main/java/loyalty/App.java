package loyalty;

import io.infinitic.workers.InfiniticWorker;

public class App {
    public static void main(String[] args) {
        try(InfiniticWorker worker = InfiniticWorker.fromConfigResource("/infinitic.yml")) {
            worker.start();
        }
    }
}
