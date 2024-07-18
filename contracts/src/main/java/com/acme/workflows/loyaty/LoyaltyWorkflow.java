package com.acme.workflows.loyaty;

import io.infinitic.annotations.Name;

@Name(name = "LoyaltyWorkflow")
public interface LoyaltyWorkflow {
    long getPoints();

    void start();

    void addBonus(BonusEvent event);

    PointStatus burn(long amount);
}