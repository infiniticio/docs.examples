package com.acme.workflows.saga.kotlin

import com.acme.services.carRental.CarRentalCart
import com.acme.services.carRental.CarRentalResult
import com.acme.services.carRental.CarRentalService
import com.acme.services.flightBooking.FlightBookingCart
import com.acme.services.flightBooking.FlightBookingResult
import com.acme.services.flightBooking.FlightBookingService
import com.acme.services.hotelBooking.HotelBookingCart
import com.acme.services.hotelBooking.HotelBookingResult
import com.acme.services.hotelBooking.HotelBookingService
import com.acme.utils.AbstractWorkflow
import com.acme.workflows.saga.SagaResult
import com.acme.workflows.saga.SagaWorkflow

@Suppress("unused")
class SagaWorkflowImpl : AbstractWorkflow(), SagaWorkflow {
    // create stub for CarRentalService
    private val carRentalService = newService(CarRentalService::class.java)

    // create stub for FlightBookingService
    private val flightBookingService = newService(FlightBookingService::class.java)

    // create stub for HotelBookingService
    private val hotelBookingService = newService(HotelBookingService::class.java)

    override fun book(
        carRentalCart: CarRentalCart,
        flightCart: FlightBookingCart,
        hotelCart: HotelBookingCart
    ): SagaResult {
        log("Saga started")

        // dispatch parallel bookings using car, flight and hotel services
        val deferredCarRental = dispatch(carRentalService::book, carRentalCart)
        val deferredFlightBooking = dispatch(flightBookingService::book, flightCart)
        val deferredHotelBooking = dispatch(hotelBookingService::book, hotelCart)

        // wait and get result of deferred CarRentalService::book
        val carRentalResult = deferredCarRental.await()

        // wait and get result of deferred FlightService::book
        val flightResult = deferredFlightBooking.await()

        // wait and get result of deferred HotelService::book
        val hotelResult =  deferredHotelBooking.await()

        // if at least one of the booking is failed than cancel all successful bookings
        if (carRentalResult == CarRentalResult.FAILURE ||
            flightResult == FlightBookingResult.FAILURE ||
            hotelResult == HotelBookingResult.FAILURE
        ) {
            if (carRentalResult == CarRentalResult.SUCCESS) { carRentalService.cancel(carRentalCart) }
            if (flightResult == FlightBookingResult.SUCCESS) { flightBookingService.cancel(flightCart) }
            if (hotelResult == HotelBookingResult.SUCCESS) { hotelBookingService.cancel(hotelCart) }

            // printing is done through an inline task
            log("Saga failed")

            return SagaResult.FAILURE
        }

        // printing is done through an inline task
        log("Saga succeeded")

        return SagaResult.SUCCESS
    }
}
