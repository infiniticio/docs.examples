package com.acme.services.flightBooking.java;

import com.acme.contracts.services.flightBooking.FlightBookingCart;
import com.acme.contracts.services.flightBooking.FlightBookingResult;
import com.acme.contracts.services.flightBooking.FlightBookingService;
import com.acme.common.AbstractService;
import com.acme.common.ExponentialBackoffRetry;
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
            log("flight booking request rejected");
            return FlightBookingResult.REJECTED;
        }

//        if (r >= 3800 ) {
//            log("flight booking service failed!");
//            throw new RuntimeException("failing request");
//        }

        log("flight booking succeeded");
        return FlightBookingResult.SUCCESS;
    }

    @Override
    public void rollback(FlightBookingCart cart) {
        log("flight booking request rolled back");
    }
}