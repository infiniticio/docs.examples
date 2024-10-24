package com.acme.workflows.saga.java;

import com.acme.contracts.services.carRental.CarRentalCart;
import com.acme.contracts.services.carRental.CarRentalResult;
import com.acme.contracts.services.carRental.CarRentalService;
import com.acme.contracts.services.flightBooking.FlightBookingCart;
import com.acme.contracts.services.flightBooking.FlightBookingResult;
import com.acme.contracts.services.flightBooking.FlightBookingService;
import com.acme.contracts.services.hotelBooking.HotelBookingCart;
import com.acme.contracts.services.hotelBooking.HotelBookingResult;
import com.acme.contracts.services.hotelBooking.HotelBookingService;
import com.acme.common.AbstractWorkflow;
import com.acme.contracts.workflows.saga.SagaResult;
import com.acme.contracts.workflows.saga.SagaWorkflow;
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

        // if at least one of the booking can not be completed than rollback all successful ones
        if (carRentalResult == CarRentalResult.REJECTED ||
            flightResult == FlightBookingResult.REJECTED ||
            hotelResult == HotelBookingResult.REJECTED
        ) {
            if (carRentalResult == CarRentalResult.SUCCESS) { carRentalService.rollback(carRentalCart); }
            if (flightResult == FlightBookingResult.SUCCESS) { flightBookingService.rollback(flightCart); }
            if (hotelResult == HotelBookingResult.SUCCESS) { hotelBookingService.rollback(hotelCart); }

            // printing is done through an inline task
            log("Saga roll backed");

            return SagaResult.FAILURE;
        }
        // printing is done through an inline task
        log("Saga succeeded");

        return SagaResult.SUCCESS;
    }
}
