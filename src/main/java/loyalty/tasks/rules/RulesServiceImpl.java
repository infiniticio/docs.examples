package loyalty.tasks.rules;

import io.infinitic.tasks.Task;
import loyalty.workflows.Event;

public class RulesServiceImpl extends Task implements RulesService {

    @Override
    public RulesResult flux(Event event, String userId){
        System.out.println("RulesService:   userId=" + userId + " event=" + event);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return RulesResult.SUCCESS;
    }
}
