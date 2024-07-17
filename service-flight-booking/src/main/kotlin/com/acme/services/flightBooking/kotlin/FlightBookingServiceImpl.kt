package com.acme.services.flightBooking.kotlin

import com.acme.services.flightBooking.FlightBookingCart
import com.acme.services.flightBooking.FlightBookingResult
import com.acme.services.flightBooking.FlightBookingService
import com.acme.utils.AbstractService.log
import com.acme.utils.ExponentialBackoffRetry
import io.infinitic.annotations.Retry
import kotlin.random.Random

@Suppress("unused")
@Retry(with = ExponentialBackoffRetry::class)
class FlightBookingServiceImpl : FlightBookingService {

    override fun book(cart: FlightBookingCart): FlightBookingResult {
        log("flight booking started...")

        // fake emulation of success/failure
        val r = Random.nextLong(0, 5000)

        return when {
            r >= 4000 -> {
                log("flight booking failed")
                FlightBookingResult.FAILURE
            }
//            r >= 3000 -> {
//                log("flight booking threw exception!")
//                throw RuntimeException("failing request")
//            }
            else -> {
                log("flight booking succeeded")
                FlightBookingResult.SUCCESS
            }
        }
    }

    override fun cancel(cart: FlightBookingCart) {
        log("flight booking canceled!")
    }
}
