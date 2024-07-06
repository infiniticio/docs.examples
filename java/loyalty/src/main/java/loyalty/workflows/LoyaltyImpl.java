package loyalty.workflows;

import io.infinitic.workflows.Workflow;

import java.time.*;

public class LoyaltyImpl extends Workflow implements Loyalty {

//    private final Integer weekInSeconds = 3600*24*7;
    private final Integer weekInSeconds = 4;

    private Integer points = 0;

    @Override
    public Integer getPoints() {
        return points;
    }

    @Override
    public void start() {
        Instant now = inline(Instant::now);

        int w = 0;

        while (w < 56) {
            inlineVoid(() -> System.out.println("points = " + points));

            // every week, a new point is added
            w++;
            timer(now.plusSeconds(w * weekInSeconds)).await();
            points++;
        }
    }

    @Override
    public void addBonus(BonusEvent event) {
        inlineVoid(() -> System.out.println("received " + event));

        switch (event) {
            case REGISTRATION_COMPLETED:
                points+= 100;
                break;

            case FORM_COMPLETED:
                points+= 200;
                break;

            case ORDER_COMPLETED:
                points+= 500;
                break;
        }
    }
}
