package com.acme.services.hotelBooking.java;

import com.acme.services.hotelBooking.HotelBookingCart;
import com.acme.services.hotelBooking.HotelBookingResult;
import com.acme.services.hotelBooking.HotelBookingService;
import com.acme.utils.AbstractService;
import com.acme.utils.ExponentialBackoffRetry;
import io.infinitic.annotations.Retry;

import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Retry(with = ExponentialBackoffRetry.class)
public class HotelBookingServiceImpl extends AbstractService implements HotelBookingService {
    @Override @NotNull
    public HotelBookingResult book(HotelBookingCart cart) {
        // fake emulation of success/failure
        log("hotel booking started...");

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

        // Uncomment those lines to emulate failures and retries
//        if (r >= 3000 ) {
//            log("hotel booking threw exception!");
//            throw new RuntimeException("failing request");
//        }

        log("hotel booking succeeded");
        return HotelBookingResult.SUCCESS;
    }

    @Override
    public void cancel(HotelBookingCart cart) {
        log("hotel booking canceled");
    }
}