package example.booking.workflows;

import example.booking.services.flight.FlightBookingCart;
import example.booking.services.hotel.HotelBookingCart;
import example.booking.services.carRental.CarRentalCart;
import io.infinitic.annotations.Name;

@Name(name = "BookingWorkflow")
public interface BookingWorkflow {
    BookingResult book(
            CarRentalCart carRentalCart,
            FlightBookingCart flightCart,
            HotelBookingCart hotelCart
    );
}
