package loyalty.tasks.rewards;

import loyalty.tasks.rules.RulesResult;
import loyalty.workflows.Event;

public interface RewardsService {
    RewardsResult flux(RulesResult rules, Event event, String userId);
}