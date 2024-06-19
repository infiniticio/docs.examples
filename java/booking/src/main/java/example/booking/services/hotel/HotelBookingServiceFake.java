package example.booking.services.hotel;

import example.booking.services.ExponentialBackoffRetry;
import io.infinitic.annotations.Retry;
import io.infinitic.tasks.Task;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Retry(with = ExponentialBackoffRetry.class)
public class HotelBookingServiceFake implements HotelBookingService {
    @Override @NotNull
    public HotelBookingResult book(HotelBookingCart cart) {
        // fake emulation of success/failure
        log("start hotel booking...");

        long r = (long) (Math.random() * 5000);

        try {
            Thread.sleep(r);
        } catch (InterruptedException e) {
            throw new RuntimeException("interrupted");
        }

        if (r >= 4000) {
            log("hotel booking failed");
            return HotelBookingResult.FAILURE;
        }

        if (r >= 3000 ) {
            log("hotel booking threw exception!");
            throw new RuntimeException("failing request");
        }

        log("hotel booking succeeded");
        return HotelBookingResult.SUCCESS;
    }

    @Override
    public void cancel(HotelBookingCart cart) {
        log("hotel booking canceled");
    }

    private void log(String msg) {
        System.out.println(Task.getWorkflowId() + " - " + Task.getServiceName() + " - " + msg);
    }
}