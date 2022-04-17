package loyalty;

import io.infinitic.factory.InfiniticWorkerFactory;
import io.infinitic.workers.InfiniticWorker;
import org.apache.pulsar.client.admin.PulsarAdminException;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException, PulsarAdminException {
        try(InfiniticWorker worker = InfiniticWorkerFactory.fromConfigResource("/infinitic.yml")) {
            worker.start();
        }
    }
}
