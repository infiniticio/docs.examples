package loyalty.tasks.rules;

import io.infinitic.annotations.Name;
import loyalty.workflows.Event;

@Name(name = "RulesService")
public interface RulesService {
    RulesResult flux(Event event, String userId);
}
