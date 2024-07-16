package com.acme.services.flightBooking.java;

import com.acme.services.flightBooking.FlightBookingCart;
import com.acme.services.flightBooking.FlightBookingResult;
import com.acme.services.flightBooking.FlightBookingService;
import com.acme.utils.AbstractService;
import com.acme.utils.ExponentialBackoffRetry;
import io.infinitic.annotations.Retry;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@Retry(with = ExponentialBackoffRetry.class)
public class FlightBookingServiceImpl extends AbstractService implements FlightBookingService {
    @Override @NotNull
    public FlightBookingResult book(FlightBookingCart cart) {
        // fake emulation of success/failure
        log("flight booking started...");

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

        // Uncomment those lines to emulate failures and retries
//        if (r >= 3000 ) {
//            log("flight booking threw exception!");
//            throw new RuntimeException("failing request");
//        }

        log("flight booking succeeded");
        return FlightBookingResult.SUCCESS;
    }

    @Override
    public void cancel(FlightBookingCart cart) {
        log("flight booking canceled");
    }
}