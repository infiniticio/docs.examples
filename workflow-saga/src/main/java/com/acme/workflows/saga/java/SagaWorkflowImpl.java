package com.acme.workflows.saga.java;

import com.acme.services.carRental.CarRentalCart;
import com.acme.services.carRental.CarRentalResult;
import com.acme.services.carRental.CarRentalService;
import com.acme.services.flightBooking.FlightBookingCart;
import com.acme.services.flightBooking.FlightBookingResult;
import com.acme.services.flightBooking.FlightBookingService;
import com.acme.services.hotelBooking.HotelBookingCart;
import com.acme.services.hotelBooking.HotelBookingResult;
import com.acme.services.hotelBooking.HotelBookingService;
import com.acme.utils.AbstractWorkflow;
import com.acme.workflows.saga.SagaResult;
import com.acme.workflows.saga.SagaWorkflow;
import io.infinitic.workflows.Deferred;

@SuppressWarnings("unused")
public class SagaWorkflowImpl extends AbstractWorkflow implements SagaWorkflow {
    // create stub for CarRentalService
    private final CarRentalService carRentalService = newService(CarRentalService.class);
    // create stub for FlightBookingService
    private final FlightBookingService flightBookingService = newService(FlightBookingService.class);
    // create stub for HotelBookingService
    private final HotelBookingService hotelBookingService = newService(HotelBookingService.class);

    @Override
    public SagaResult book(
            CarRentalCart carRentalCart,
            FlightBookingCart flightCart,
            HotelBookingCart hotelCart
    ) {
        log("Saga started");

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
            log("Saga failed");

            return SagaResult.FAILURE;
        }
        // printing is done through an inline task
        log("Saga succeeded");

        return SagaResult.SUCCESS;
    }
}
