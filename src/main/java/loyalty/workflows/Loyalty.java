package loyalty.workflows;

import io.infinitic.workflows.SendChannel;

public interface Loyalty {
    Integer getPoints();

    void start();

    void addBonus(BonusEvent event);
}