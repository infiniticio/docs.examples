package loyalty.workflows;

import io.infinitic.workflows.SendChannel;

public interface LoyaltyWorkflow {
    SendChannel<Event> getEventChannel();

    void start(String userId);
}
