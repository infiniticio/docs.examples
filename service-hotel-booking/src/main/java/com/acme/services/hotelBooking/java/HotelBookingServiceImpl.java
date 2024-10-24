package com.acme.services.hotelBooking.java;

import com.acme.contracts.services.hotelBooking.HotelBookingCart;
import com.acme.contracts.services.hotelBooking.HotelBookingResult;
import com.acme.contracts.services.hotelBooking.HotelBookingService;
import com.acme.common.AbstractService;
import com.acme.common.ExponentialBackoffRetry;
import io.infinitic.annotations.Retry;


@SuppressWarnings("unused")
@Retry(with = ExponentialBackoffRetry.class)
public class HotelBookingServiceImpl extends AbstractService implements HotelBookingService {
    @Override 
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
            log("hotel booking request rejected");
            return HotelBookingResult.REJECTED;
        }

        // Uncomment those lines to emulate failures and retries
//        if (r >= 3800 ) {
//            log("hotel booking threw exception!");
//            throw new RuntimeException("failing request");
//        }

        log("hotel booking succeeded");
        return HotelBookingResult.SUCCESS;
    }

    @Override
    public void rollback(HotelBookingCart cart) {
        log("hotel booking request rolled back");
    }
}