package example.booking.workflows;

import example.booking.services.carRental.*;
import example.booking.services.flight.*;
import example.booking.services.hotel.*;
import io.infinitic.workflows.*;

@SuppressWarnings("unused")
public class BookingWorkflowImpl extends Workflow implements BookingWorkflow {
    // create stub for CarRentalService
    private final CarRentalService carRentalService = newService(CarRentalService.class);
    // create stub for FlightBookingService
    private final FlightBookingService flightBookingService = newService(FlightBookingService.class);
    // create stub for HotelBookingService
    private final HotelBookingService hotelBookingService = newService(HotelBookingService.class);

    @Override
    public BookingResult book(
            CarRentalCart carRentalCart,
            FlightBookingCart flightCart,
            HotelBookingCart hotelCart
    ) {
        log("booking started");

        // dispatch parallel bookings using car, flight and hotel services
        Deferred<CarRentalResult> deferredCarRental = dispatch(carRentalService::book, carRentalCart);
        Deferred<FlightBookingResult> deferredFlightBooking = dispatch(flightBookingService::book, flightCart);
        Deferred<HotelBookingResult> deferredHotelBooking = dispatch(hotelBookingService::book, hotelCart);

        // wait and get result of deferred CarRentalService::book
        CarRentalResult carRentalResult = deferredCarRental.await();
        // wait and get result of deferred FlightService::book
        FlightBookingResult flightResult = deferredFlightBooking.await();
        // wait and get result of deferred HotelService::book
        HotelBookingResult hotelResult = deferredHotelBooking.await();

        // if at least one of the booking is failed than cancel all successful bookings
        if (carRentalResult == CarRentalResult.FAILURE ||
            flightResult == FlightBookingResult.FAILURE ||
            hotelResult == HotelBookingResult.FAILURE
        ) {
            if (carRentalResult == CarRentalResult.SUCCESS) { carRentalService.cancel(carRentalCart); }
            if (flightResult == FlightBookingResult.SUCCESS) { flightBookingService.cancel(flightCart); }
            if (hotelResult == HotelBookingResult.SUCCESS) { hotelBookingService.cancel(hotelCart); }

            // printing is done through an inline task
            log("booking failed");

            return BookingResult.FAILURE;
        }
        // printing is done through an inline task
        log("booking succeeded");

        return BookingResult.SUCCESS;
    }

    private void log(String msg) {
        inlineVoid(() -> System.out.println(getWorkflowId() + " - " + getWorkflowName() + " - " + msg));
    }
}
