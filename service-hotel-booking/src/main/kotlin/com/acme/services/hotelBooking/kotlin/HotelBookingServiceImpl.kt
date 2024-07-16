package com.acme.services.hotelBooking.kotlin

import com.acme.services.hotelBooking.HotelBookingCart
import com.acme.services.hotelBooking.HotelBookingResult
import com.acme.services.hotelBooking.HotelBookingService
import com.acme.utils.AbstractService.log
import com.acme.utils.ExponentialBackoffRetry
import io.infinitic.annotations.Retry
import kotlin.random.Random

@Suppress("unused")
@Retry(with = ExponentialBackoffRetry::class)
class HotelBookingServiceImpl : HotelBookingService {

    override fun book(cart: HotelBookingCart): HotelBookingResult {
        log("hotel booking started...")

        // fake emulation of success/failure
        val r = Random.nextLong(0, 5000)

        return when {
            r >= 4000 -> {
                log("hotel booking failed")
                HotelBookingResult.FAILURE
            }
//            r >= 3000 -> {
//                log("hotel booking threw exception!")
//                throw RuntimeException("failing request")
//            }
            else -> {
                log("hotel booking succeeded")
                HotelBookingResult.SUCCESS
            }
        }
    }

    override fun cancel(cart: HotelBookingCart) {
        log("hotel booking canceled!")
    }
}
