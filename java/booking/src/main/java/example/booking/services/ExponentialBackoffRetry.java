package example.booking.services;

import io.infinitic.tasks.Task;
import io.infinitic.tasks.WithRetry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExponentialBackoffRetry implements WithRetry {
    // Exponential backoff retry strategy up to 6 attempts
    @Nullable @Override
    public Double getSecondsBeforeRetry(int attempt, @NotNull Exception e) {
        Double delay;

        if (attempt < 6) {
            delay = 5 * Math.random() * Math.pow(2.0, attempt);
            log("retry after " + String.format("%.2f", delay) + " seconds");
        } else {
            delay = null;
            log("failed after all retries");
        }

        return delay;
    }

    private void log(String msg) {
        System.out.println(Task.getWorkflowId() + " - " + Task.getServiceName() + " - " + msg);
    }
}
