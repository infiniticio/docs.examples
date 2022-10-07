package loyalty.workflows;

import io.infinitic.annotations.Name;
import io.infinitic.workflows.SendChannel;

@Name(name = "LoyaltyWorkflow")
public interface LoyaltyWorkflow {
    SendChannel<Boolean> getTerminationChannel();

    void start(String userId);

    void receive(Event e);
}
