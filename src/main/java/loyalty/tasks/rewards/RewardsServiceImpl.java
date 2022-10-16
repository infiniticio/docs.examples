package loyalty.tasks.rewards;

import loyalty.tasks.rules.RulesResult;
import loyalty.workflows.Event;

@SuppressWarnings("unused")
public class RewardsServiceImpl implements RewardsService {

    @Override
    public RewardsResult flux(RulesResult rules, Event event, String userId) {
        System.out.println("RewardsService: userId=" + userId + " event=" + event + " rules=" + rules);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return RewardsResult.SUCCESS;
    }
}