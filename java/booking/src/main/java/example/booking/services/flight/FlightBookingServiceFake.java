package example.booking.services.flight;

import example.booking.services.ExponentialBackoffRetry;
import io.infinitic.annotations.Retry;
import io.infinitic.tasks.Task;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@Retry(with = ExponentialBackoffRetry.class)
public class FlightBookingServiceFake implements FlightBookingService {
    @Override @NotNull
    public FlightBookingResult book(FlightBookingCart cart) {
        // fake emulation of success/failure
        log("start flight booking ...");

        long r = (long) (Math.random() * 5000);
        try {
            Thread.sleep(r);
        } catch (InterruptedException e) {
            throw new RuntimeException("interrupted");
        }

        if (r >= 4000) {
            log("flight booking failed");
            return FlightBookingResult.FAILURE;
        }

        if (r >= 3000 ) {
            log("flight booking threw exception!");
            throw new RuntimeException("failing request");
        }

        log("flight booking succeeded");
        return FlightBookingResult.SUCCESS;
    }

    @Override
    public void cancel(FlightBookingCart cart) {
        log("flight booking canceled");
    }

    private void log(String msg) {
        System.out.println(Task.getWorkflowId() + " - " + Task.getServiceName() + " - " + msg);
    }
}