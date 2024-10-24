package com.acme.contracts.services.flightBooking;

import io.infinitic.annotations.Name;

@Name(name = "FlightBooking")
public interface FlightBookingService {
    FlightBookingResult book(FlightBookingCart cart);

    void rollback(FlightBookingCart cart);
}