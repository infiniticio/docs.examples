package loyalty.tasks.loyalpi;

import loyalty.tasks.rules.RulesResult;
import loyalty.workflows.Event;

public interface LoyalpiService {
    LoyalpiResult flux(RulesResult rules, Event event, String userId);
}