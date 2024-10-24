package com.acme.workflows.saga.kotlin

import com.acme.contracts.services.carRental.CarRentalCart
import com.acme.contracts.services.carRental.CarRentalResult
import com.acme.contracts.services.carRental.CarRentalService
import com.acme.contracts.services.flightBooking.FlightBookingCart
import com.acme.contracts.services.flightBooking.FlightBookingResult
import com.acme.contracts.services.flightBooking.FlightBookingService
import com.acme.contracts.services.hotelBooking.HotelBookingCart
import com.acme.contracts.services.hotelBooking.HotelBookingResult
import com.acme.contracts.services.hotelBooking.HotelBookingService
import com.acme.common.AbstractWorkflow
import com.acme.contracts.workflows.saga.SagaResult
import com.acme.contracts.workflows.saga.SagaWorkflow

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
        if (carRentalResult == CarRentalResult.REJECTED ||
            flightResult == FlightBookingResult.REJECTED ||
            hotelResult == HotelBookingResult.REJECTED
        ) {
            if (carRentalResult == CarRentalResult.SUCCESS) { carRentalService.rollback(carRentalCart) }
            if (flightResult == FlightBookingResult.SUCCESS) { flightBookingService.rollback(flightCart) }
            if (hotelResult == HotelBookingResult.SUCCESS) { hotelBookingService.rollback(hotelCart) }

            // printing is done through an inline task
            log("Saga rolled back")

            return SagaResult.FAILURE
        }

        // printing is done through an inline task
        log("Saga succeeded")

        return SagaResult.SUCCESS
    }
}
