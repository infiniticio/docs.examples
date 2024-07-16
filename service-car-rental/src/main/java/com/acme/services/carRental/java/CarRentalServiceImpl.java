package com.acme.services.carRental.java;

import com.acme.services.carRental.CarRentalCart;
import com.acme.services.carRental.CarRentalResult;
import com.acme.services.carRental.CarRentalService;
import com.acme.utils.AbstractService;
import com.acme.utils.ExponentialBackoffRetry;
import io.infinitic.annotations.Retry;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;


@SuppressWarnings("unused")
@Retry(with = ExponentialBackoffRetry.class)
public class CarRentalServiceImpl extends AbstractService implements CarRentalService {
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("hh:mm:ss.SSS");

    @Override @NotNull
    public CarRentalResult book(CarRentalCart cart) {
        // fake emulation of success/failure
        log("car rental started...");

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

        // Uncomment those lines to emulate failures and retries
//        if (r >= 3000 ) {
//            Service.log("car rental threw exception!");
//            throw new RuntimeException("failing request");
//        }

        log("car rental succeeded");
        return CarRentalResult.SUCCESS;
    }

    @Override
    public void cancel(CarRentalCart cart) {
        log("car rental canceled");
    }

}
