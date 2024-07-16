package com.acme.services.carRental.kotlin

import com.acme.services.carRental.CarRentalCart
import com.acme.services.carRental.CarRentalResult
import com.acme.services.carRental.CarRentalService
import com.acme.utils.AbstractService.log
import com.acme.utils.ExponentialBackoffRetry
import io.infinitic.annotations.Retry
import kotlin.random.Random

@Suppress("unused")
@Retry(with = ExponentialBackoffRetry::class)
class CarRentalServiceImpl : CarRentalService {

    override fun book(cart: CarRentalCart): CarRentalResult {
        log("car rental started...")

        // fake emulation of success/failure
        val r = Random.nextLong(0, 5000)

        return when {
            r >= 4000 -> {
                log("car rental failed")
                CarRentalResult.FAILURE
            }
//            r >= 3000 -> {
//                log("car rental threw exception!")
//                throw RuntimeException("failing request")
//            }
            else -> {
                log("car rental succeeded")
                CarRentalResult.SUCCESS
            }
        }
    }

    override fun cancel(cart: CarRentalCart) {
        log("car rental canceled!")
    }
}
