package loyalty.workflows;

import io.infinitic.annotations.Name;

@Name(name = "Loyalty")
public interface Loyalty {
    Integer getPoints();

    void start();

    void addBonus(BonusEvent event);
}