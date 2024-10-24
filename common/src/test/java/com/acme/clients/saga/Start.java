package com.acme.clients.saga;

import com.acme.common.AbstractClient;
import com.acme.contracts.services.carRental.CarRentalCart;
import com.acme.contracts.services.flightBooking.FlightBookingCart;
import com.acme.contracts.services.hotelBooking.HotelBookingCart;
import com.acme.contracts.workflows.saga.SagaWorkflow;
import io.infinitic.clients.InfiniticClient;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Start extends AbstractClient {
    public static final String sagaTag = "saga";

    public static void main(String[] args) {
        try(InfiniticClient client = InfiniticClient.fromYamlResource("/infinitic.yml")) {
            // create a stub for BookingWorkflow
            SagaWorkflow sagaWorkflow = client.newWorkflow(SagaWorkflow.class, Set.of(sagaTag));

            long[] ns = getLongs(args);
            long n = ns.length == 0 ? 1 : ns[0];

            CompletableFuture<?>[] futures = new CompletableFuture<?>[(int) n];
            for (int i = 0; i < n; i++) {
                // faking some carts
                CarRentalCart carRentalCart = new CarRentalCart();
                FlightBookingCart flightCart = new FlightBookingCart();
                HotelBookingCart hotelCart = new HotelBookingCart();

                // asynchronous dispatch of workflow
                String strI = String.valueOf(i);
                futures[i] = client.dispatchAsync(sagaWorkflow::book, carRentalCart, flightCart, hotelCart)
                        .thenApply(deferred -> printDispatched("Workflow " + strI, deferred.getId()))
                        .exceptionally(error -> printError(strI, error));
            }
            CompletableFuture.allOf(futures).join();
        }
    }
}