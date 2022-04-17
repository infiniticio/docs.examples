package loyalty.tasks.rules;

import loyalty.workflows.Event;

public interface RulesService {
    RulesResult flux(Event event, String userId);
}
