package com.acme.services.hotelBooking;

import io.infinitic.annotations.Name;

@Name(name = "HotelBooking")
public interface HotelBookingService {
    HotelBookingResult book(HotelBookingCart cart);

    void cancel(HotelBookingCart cart);
}