package loyalty.workflows;

import io.infinitic.workflows.Channel;
import io.infinitic.workflows.Deferred;
import io.infinitic.workflows.Workflow;
import loyalty.tasks.rules.RulesResult;
import loyalty.tasks.rules.RulesService;
import loyalty.tasks.loyalpi.LoyalpiService;
import loyalty.tasks.rewards.RewardsService;

import static loyalty.workflows.Event.USER_TERMINATED;

public class LoyaltyWorkflowImpl extends Workflow implements LoyaltyWorkflow {

    // event channel
    private final Channel<Event> eventChannel = channel();

    @Override
    public Channel<Event> getEventChannel() {
        return eventChannel;
    }

    // create stub for RulesService
    private final RulesService rulesService = newTask(RulesService.class);
    // create stub for LoyalpiService
    private final LoyalpiService loyalpiService = newTask(LoyalpiService.class);
    // create stub for RewardsService
    private final RewardsService rewardsService = newTask(RewardsService.class);


    @Override
    public void start(String userId) {
        inlineVoid(() -> System.out.println("LoyaltyWorkflow: starting for userId=" + userId));

        // make sure to catch signals
        Deferred<Event> deferredEvent = getEventChannel().receive();

        // wait for next signal
        Event event = deferredEvent.await();


        while (event != USER_TERMINATED) {
            // Call Microservice 1 by doing an HTTP call
            RulesResult rulesOutcomes = rulesService.flux(event, userId);

            // Call Microservice 2 by doing an HTTP call and using the result of microservice 1
            loyalpiService.flux(rulesOutcomes, event, userId);

            // Call Microservice 3 by doing an HTTP call and using the result of microservice 1
            rewardsService.flux(rulesOutcomes, event, userId);

            // wait for next signal
            event = deferredEvent.await();
        }

        inlineVoid(() -> System.out.println("Workflow terminated for userId=" + userId));
    }
}
