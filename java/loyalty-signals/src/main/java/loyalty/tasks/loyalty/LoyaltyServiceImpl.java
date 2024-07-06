package loyalty.tasks.loyalty;

import loyalty.tasks.rules.RulesResult;
import loyalty.workflows.Event;

@SuppressWarnings("unused")
public class LoyaltyServiceImpl implements LoyaltyService {

    @Override
    public LoyaltyResult flux(RulesResult rules, Event event, String userId) {
        System.out.println("LoyaltyService: userId=" + userId + " event=" + event + " rules=" + rules);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return LoyaltyResult.SUCCESS;
    }
}