package loyalty.tasks.rewards;

import io.infinitic.tasks.Task;
import loyalty.tasks.rules.RulesResult;
import loyalty.workflows.Event;

public class RewardsServiceImpl extends Task implements RewardsService {

    @Override
    public RewardsResult flux(RulesResult rules, Event event, String userId) {
        System.out.println("RewardsService: userId=" + userId + " event=" + event + " rules=" + rules);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return RewardsResult.SUCCESS;
    }
}