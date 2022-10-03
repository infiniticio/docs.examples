package example.booking.tasks.hotel;

import io.infinitic.annotations.Name;

@Name(name = "HotelBooking")
public interface HotelBookingService {
    HotelBookingResult book(HotelBookingCart cart);

    void cancel(HotelBookingCart cart);
}