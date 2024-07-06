package loyalty.tasks.rewards;

import io.infinitic.annotations.Name;
import loyalty.tasks.rules.RulesResult;
import loyalty.workflows.Event;

@Name(name = "RewardsService")
public interface RewardsService {
    RewardsResult flux(RulesResult rules, Event event, String userId);
}