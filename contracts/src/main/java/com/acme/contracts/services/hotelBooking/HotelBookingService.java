package com.acme.contracts.services.hotelBooking;

import io.infinitic.annotations.Name;

@Name(name = "HotelBooking")
public interface HotelBookingService {
    HotelBookingResult book(HotelBookingCart cart);

    void rollback(HotelBookingCart cart);
}