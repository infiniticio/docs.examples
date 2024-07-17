package com.acme.workflows.loyalty.java;

import com.acme.utils.AbstractWorkflow;
import com.acme.workflows.loyaty.BonusEvent;
import com.acme.workflows.loyaty.LoyaltyWorkflow;

import java.time.Duration;

public class LoyaltyWorkflowImpl extends AbstractWorkflow implements LoyaltyWorkflow {

    private final LoyaltyWorkflow self = getWorkflowById(LoyaltyWorkflow.class, getWorkflowId());

    private final long secondsForPointReward = 10;

    private long points = 0;

    @Override
    public long getPoints() {
        return points;
    }

    @Override
    public void start() {
        log("points = " + points);

        // every `secondsForPointReward` seconds, a new point is added
        timer(Duration.ofSeconds(secondsForPointReward)).await();
        points++;

        // Loop
        dispatchVoid(self::start);
    }

    @Override
    public void addBonus(BonusEvent event) {
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

        log("received " + event + " - new points = " + points);
    }
}
