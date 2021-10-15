package loyalty.workflows;

public interface Loyalty {
    Integer getPoints();

    void start();

    Integer bonus(Integer value);
}