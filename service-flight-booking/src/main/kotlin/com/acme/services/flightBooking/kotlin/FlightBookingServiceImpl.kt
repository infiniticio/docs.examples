package com.acme.services.flightBooking.kotlin

import com.acme.contracts.services.flightBooking.FlightBookingCart
import com.acme.contracts.services.flightBooking.FlightBookingResult
import com.acme.contracts.services.flightBooking.FlightBookingService
import com.acme.common.AbstractService.log
import com.acme.common.ExponentialBackoffRetry
import io.infinitic.annotations.Retry
import io.infinitic.common.serDe.SerializedData
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
                log("flight booking request rejected")
                FlightBookingResult.REJECTED
            }
//            r >= 3800 -> {
//                log("flight booking service failed!")
//                throw RuntimeException("failing request")
//            }
            else -> {
                log("flight booking succeeded")
                FlightBookingResult.SUCCESS
            }
        }
    }

    override fun rollback(cart: FlightBookingCart) {
        log("flight booking request rolled back")
    }
}