package loyalty;

import loyalty.workflows.BonusEvent;
import loyalty.workflows.Loyalty;
import io.infinitic.client.Deferred;
import io.infinitic.client.InfiniticClient;
import io.infinitic.factory.InfiniticClientFactory;

import java.util.HashSet;
import java.util.Set;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        try(InfiniticClient client = InfiniticClientFactory.fromConfigFile("infinitic.yml")) {
            // create a stub from HelloWorld interface
            Set tags = new HashSet();
            tags.add("<userId>");
            Loyalty loyalty = client.newWorkflow(Loyalty.class, tags);

            // asynchronous dispatch of a workflow
            Deferred<Void> deferred = client.dispatchVoid(loyalty::start);
            System.out.println("workflow " + Loyalty.class.getName() + " " + deferred.getId() + " dispatched!");

            // get a reference to this workflow
            Loyalty w = client.getWorkflowById(Loyalty.class, deferred.getId());

            Thread.sleep(5000);
            System.out.println("Points: " + w.getPoints());

            Thread.sleep(5000);
            client.dispatchVoid(w::addBonus, BonusEvent.REGISTRATION_COMPLETED);
            System.out.println("bonus!");

            Thread.sleep(5000);
            System.out.println("Points: " + w.getPoints());

            Thread.sleep(5000);
            client.dispatchVoid(w::addBonus, BonusEvent.ORDER_COMPLETED);
            System.out.println("bonus!");

            Thread.sleep(5000);
            System.out.println("Points: " + w.getPoints());

            Thread.sleep(5000);
            client.cancel(w);
            System.out.println("Workflow canceled");
        }
    }
}
