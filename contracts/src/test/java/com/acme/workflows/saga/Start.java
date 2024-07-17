package com.acme.workflows.saga;

import com.acme.workflows.utils.AbstractClient;
import com.acme.services.carRental.CarRentalCart;
import com.acme.services.flightBooking.FlightBookingCart;
import com.acme.services.hotelBooking.HotelBookingCart;
import io.infinitic.clients.InfiniticClient;

import java.util.Set;

public class Start extends AbstractClient {
    public static final String sagaTag = "saga";

    public static void main(String[] args) {
        try(InfiniticClient client = InfiniticClient.fromConfigResource("/infinitic.yml")) {
            // create a stub for BookingWorkflow
            SagaWorkflow sagaWorkflow = client.newWorkflow(SagaWorkflow.class, Set.of(sagaTag));

            int i = 0;
            while (i < AbstractClient.getLong(args)) {
                // faking some carts
                CarRentalCart carRentalCart = new CarRentalCart();
                FlightBookingCart flightCart = new FlightBookingCart();
                HotelBookingCart hotelCart = new HotelBookingCart();

                // asynchronous dispatch of workflow
                String strI = String.valueOf(i);
                client.dispatchAsync(sagaWorkflow::book, carRentalCart, flightCart, hotelCart)
                        .thenApply(deferred -> AbstractClient.printDispatched("Workflow " + strI, deferred.getId()))
                        .exceptionally(error -> AbstractClient.printError(strI, error));
                i++;
            }
        }
    }
}