package com.acme.services.carRental.java;

import com.acme.contracts.services.carRental.CarRentalCart;
import com.acme.contracts.services.carRental.CarRentalResult;
import com.acme.contracts.services.carRental.CarRentalService;
import com.acme.common.AbstractService;
import com.acme.common.ExponentialBackoffRetry;
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
           log("car rental request rejected");
            return CarRentalResult.REJECTED;
        }

        // Uncomment those lines to emulate failures and retries
//        if (r >= 3800 ) {
//            log("car rental service failed!");
//            throw new RuntimeException("failing request");
//        }

        log("car rental succeeded");
        return CarRentalResult.SUCCESS;
    }

    @Override
    public void rollback(CarRentalCart cart) {
        log("car rental request rolled back");
    }

}
