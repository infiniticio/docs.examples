package loyalty.tasks.loyalpi;

import io.infinitic.tasks.Task;
import loyalty.tasks.rules.RulesResult;
import loyalty.workflows.Event;

public class LoyalpiServiceImpl extends Task implements LoyalpiService {

    @Override
    public LoyalpiResult flux(RulesResult rules, Event event, String userId) {
        System.out.println("LoyalpiService: userId=" + userId + " event=" + event + " rules=" + rules);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return LoyalpiResult.SUCCESS;
    }
}