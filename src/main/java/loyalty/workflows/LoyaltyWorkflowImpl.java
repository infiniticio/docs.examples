package loyalty.workflows;

import io.infinitic.workflows.Channel;
import io.infinitic.workflows.Workflow;
import loyalty.tasks.loyalpi.LoyalpiService;
import loyalty.tasks.rewards.RewardsService;
import loyalty.tasks.rules.RulesResult;
import loyalty.tasks.rules.RulesService;

public class LoyaltyWorkflowImpl extends Workflow implements LoyaltyWorkflow {

    // event channel
    private final Channel<Boolean> terminationChannel = channel();

    @Override
    public Channel<Boolean> getTerminationChannel() {
        return terminationChannel;
    }

    // create stub for RulesService
    private final RulesService rulesService = newTask(RulesService.class);
    // create stub for LoyalpiService
    private final LoyalpiService loyalpiService = newTask(LoyalpiService.class);
    // create stub for RewardsService
    private final RewardsService rewardsService = newTask(RewardsService.class);

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

    public void receive(Event e) {
        bufferEvents.add(e);

        if (!ongoing) {
            ongoing = true;

            while (bufferEvents.size() > 0) {
                Event event = bufferEvents.remove(0);

                if (event == Event.USER_TERMINATED) {
                    // send termination event to self
                    getWorkflowById(LoyaltyWorkflow.class, context.getId()).getTerminationChannel().send(true);
                    // clear buffer in case we received more events after this one
                    bufferEvents.clear();
                } else {
                    // Call Microservice 1 by doing an HTTP call
                    RulesResult rulesOutcomes = rulesService.flux(event, userId);

                    // Call Microservice 2 by doing an HTTP call and using the result of microservice 1
                    loyalpiService.flux(rulesOutcomes, event, userId);

                    // Call Microservice 3 by doing an HTTP call and using the result of microservice 1
                    rewardsService.flux(rulesOutcomes, event, userId);
                }
            }

            ongoing = false;
        }
    }
}
