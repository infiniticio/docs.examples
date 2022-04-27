package loyalty;

import io.infinitic.clients.Deferred;
import io.infinitic.clients.InfiniticClient;
import loyalty.workflows.Event;
import loyalty.workflows.LoyaltyWorkflow;
import io.infinitic.factory.InfiniticClientFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Client {
    public static void main(String[] args) throws InterruptedException, IOException {
        try(InfiniticClient client = InfiniticClientFactory.fromConfigResource("/infinitic.yml")) {

            for (int i = 0; i < 1; i++) {
                String userId = UUID.randomUUID().toString();
                String tagId = "customId:userId:" + userId;
                Set<String> tags = Set.of(tagId);

                // create a stub from HelloWorld interface
                LoyaltyWorkflow loyalty = client.newWorkflow(LoyaltyWorkflow.class, tags);

                // asynchronous dispatch of a workflow
                Deferred<Void> deferred = client.dispatchVoid(loyalty::start, userId);
                System.out.println("Workflow " + deferred.getId() + " dispatched!");

                // get a reference to this workflow
                LoyaltyWorkflow w = client.getWorkflowByTag(LoyaltyWorkflow.class, tagId);

                Event event;

                event = Event.ORDER_COMPLETED;
                w.getEventChannel().send(event);
                System.out.println("Event " + event + " dispatched!");

                Thread.sleep(200);

                event = Event.REGISTRATION_COMPLETED;
                w.getEventChannel().send(event);
                System.out.println("Event " + event + " dispatched!");

                event = Event.FORM_COMPLETED;
                w.getEventChannel().send(event);
                System.out.println("Event " + event + " dispatched!");

                event = Event.USER_TERMINATED;
                w.getEventChannel().send(event);
                System.out.println("Event " + event + " dispatched!");
            }
        }
    }
}
