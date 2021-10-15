package loyalty.workflows;

import io.infinitic.workflows.Channel;
import io.infinitic.workflows.Deferred;
import io.infinitic.workflows.Workflow;

import java.time.Instant;

public class LoyaltyImpl extends Workflow implements Loyalty {
    private Integer points = 0;

    @Override
    public void start() {
        Instant now = inline(Instant::now);

        int s = 0;
        while (points < 1000) {

            inlineVoid(() -> System.out.println(context.getId() + " " + points + " points"));

            // every 5 seconds, a new point is added
            s += 5;
            timer(now.plusSeconds(s)).await();

            points++;
        }
    }

    @Override
    public Integer bonus(Integer value) {
        points += value;

        return points;
    }

    @Override
    public Integer getPoints() {
        return points;
    }
}
