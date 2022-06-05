package loyalty.workflows;

import io.infinitic.workflows.SendChannel;

public interface LoyaltyWorkflow {
    SendChannel<Boolean> getTerminationChannel();

    void start(String userId);

    void receive(Event e);
}
