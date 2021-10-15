package loyalty;

import io.infinitic.factory.InfiniticWorkerFactory;
import io.infinitic.worker.InfiniticWorker;

public class App {
    public static void main(String[] args) {
        try(InfiniticWorker worker = InfiniticWorkerFactory.fromConfigFile("infinitic.yml")) {
            worker.start();
        }
    }
}
