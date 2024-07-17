package com.acme.workflows.saga;

import com.acme.services.carRental.CarRentalCart;
import com.acme.services.flightBooking.FlightBookingCart;
import com.acme.services.hotelBooking.HotelBookingCart;
import io.infinitic.annotations.Name;

@Name(name = "SagaWorkflow")
public interface SagaWorkflow {
    SagaResult book(
            CarRentalCart carRentalCart,
            FlightBookingCart flightCart,
            HotelBookingCart hotelCart
    );
}
