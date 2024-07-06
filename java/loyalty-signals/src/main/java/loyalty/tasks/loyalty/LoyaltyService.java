package loyalty.tasks.loyalty;

import io.infinitic.annotations.Name;
import loyalty.tasks.rules.RulesResult;
import loyalty.workflows.Event;

@Name(name = "LoyaltyService")
public interface LoyaltyService {
    LoyaltyResult flux(RulesResult rules, Event event, String userId);
}