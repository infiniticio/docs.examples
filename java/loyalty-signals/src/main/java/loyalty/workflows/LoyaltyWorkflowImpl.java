package loyalty.workflows;

import io.infinitic.workflows.Channel;
import io.infinitic.workflows.Workflow;
import loyalty.tasks.loyalty.LoyaltyService;
import loyalty.tasks.rewards.RewardsService;
import loyalty.tasks.rules.RulesResult;
import loyalty.tasks.rules.RulesService;

@SuppressWarnings("unused")
public class LoyaltyWorkflowImpl extends Workflow implements LoyaltyWorkflow {

    // event channel
    private final Channel<Boolean> terminationChannel = channel();

    @Override
    public Channel<Boolean> getTerminationChannel() {
        return terminationChannel;
    }

    // create stub for RulesService
    private final RulesService rulesService = newService(RulesService.class);
    // create stub for LoyalpiService
    private final LoyaltyService loyaltyService = newService(LoyaltyService.class);
    // create stub for RewardsService
    private final RewardsService rewardsService = newService(RewardsService.class);

    private String userId;

    // we use an object to store the buffer because of https://github.com/infiniticio/infinitic/issues/80
    private final BufferEvents bufferEvents = new BufferEvents();

    private Boolean ongoing = false;

    @Override
    public void start(String userId) {
        this.userId = userId;

        inlineVoid(() -> System.out.println("LoyaltyWorkflow: starting for userId=" + userId));
        
        getTerminationChannel().receive(1).await();

        inlineVoid(() -> System.out.println("LoyaltyWorkflow: terminated for userId=" + userId));
    }

   public void execute() {
        Event event = bufferEvents.remove(0);

        if (event == Event.USER_TERMINATED) {
            // send termination event to self
            self().getTerminationChannel().send(true);
            // clear buffer in case we received more events after this one
            bufferEvents.clear();
        } else {
            // Call Microservice 1 by doing an HTTP call
            RulesResult rulesOutcomes = rulesService.flux(event, userId);

            // Call Microservice 2 by doing an HTTP call and using the result of microservice 1
            loyaltyService.flux(rulesOutcomes, event, userId);

            // Call Microservice 3 by doing an HTTP call and using the result of microservice 1
            rewardsService.flux(rulesOutcomes, event, userId);
        }

        if (bufferEvents.size() > 0) {
            dispatchVoid(self()::execute);
        } else {
            ongoing = false;
        }
    }

    public void receive(Event e) {
        // adding to the buffer
        bufferEvents.add(e);

        // ongoing is false per default
        if (!ongoing) {
            // ongoing stays true, until bufferEvents is empty
            ongoing = true;

            dispatchVoid(self()::execute);
        }
    }

    // Target itself
    private LoyaltyWorkflow self() {
        return getWorkflowById(LoyaltyWorkflow.class, getWorkflowId());
    }

}
