package com.acme.workflows.loyalty.java;

import com.acme.common.AbstractWorkflow;
import com.acme.contracts.workflows.loyaty.BonusEvent;
import com.acme.contracts.workflows.loyaty.LoyaltyWorkflow;
import com.acme.contracts.workflows.loyaty.PointStatus;
import io.infinitic.annotations.Ignore;

import java.time.Duration;

public class LoyaltyWorkflowImpl extends AbstractWorkflow implements LoyaltyWorkflow {
    // workflow stub that targets itself
    private final LoyaltyWorkflow self = getWorkflowById(LoyaltyWorkflow.class, getWorkflowId());

    @Ignore
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
            case FORM_COMPLETED:
                points+= 200;
                break;

            case ORDER_COMPLETED:
                points+= 500;
                break;
        }

        log("received " + event + " - new points = " + points);
    }

    @Override
    public PointStatus burn(long amount) {
        if (points - amount >= 0) {
            points -= amount;

            log("burnt " + amount + " - new points = " + points);

            return PointStatus.OK;
        } else {
            log("unable to burn " + amount + " - insufficient points = " + points);

            return PointStatus.INSUFFICIENT;
        }
    }
}
