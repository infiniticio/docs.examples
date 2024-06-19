package example.booking.services.carRental;

import example.booking.services.ExponentialBackoffRetry;
import io.infinitic.annotations.Retry;
import io.infinitic.tasks.Task;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@Retry(with = ExponentialBackoffRetry.class)
public class CarRentalServiceFake implements CarRentalService {
    @Override @NotNull
    public CarRentalResult book(CarRentalCart cart) {
        // fake emulation of success/failure
        log("start car rental ...");

        long r = (long) (Math.random() * 5000);
        try {
            Thread.sleep(r);
        } catch (InterruptedException e) {
            throw new RuntimeException("interrupted");
        }

        if (r >= 4000) {
            log("car rental failed");
            return CarRentalResult.FAILURE;
        }

        if (r >= 3000 ) {
            log("car rental threw exception!");
            throw new RuntimeException("failing request");
        }

        log("car rental succeeded");
        return CarRentalResult.SUCCESS;
    }

    @Override
    public void cancel(CarRentalCart cart) {
        log("car rental canceled");
    }

    private void log(String msg) {
        System.out.println(Task.getWorkflowId() + " - " + Task.getServiceName() + " - " + msg);
    }
}
