package com.acme.contracts.workflows.saga;

import com.acme.contracts.services.carRental.CarRentalCart;
import com.acme.contracts.services.flightBooking.FlightBookingCart;
import com.acme.contracts.services.hotelBooking.HotelBookingCart;
import io.infinitic.annotations.Name;

@Name(name = "SagaWorkflow")
public interface SagaWorkflow {
    SagaResult book(
            CarRentalCart carRentalCart,
            FlightBookingCart flightCart,
            HotelBookingCart hotelCart
    );
}
