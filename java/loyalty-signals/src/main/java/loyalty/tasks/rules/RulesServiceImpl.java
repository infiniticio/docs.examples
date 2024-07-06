package loyalty.tasks.rules;

import loyalty.workflows.Event;

@SuppressWarnings("unused")
public class RulesServiceImpl implements RulesService {

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
