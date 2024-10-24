package com.acme.services.carRental.kotlin

import com.acme.contracts.services.carRental.CarRentalCart
import com.acme.contracts.services.carRental.CarRentalResult
import com.acme.contracts.services.carRental.CarRentalService
import com.acme.common.AbstractService.log
import com.acme.common.ExponentialBackoffRetry
import io.infinitic.annotations.Batch
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
                log("car rental request rejected")
                CarRentalResult.REJECTED
            }

//            r >= 3800 -> {
//                log("car rental service failed!")
//                throw RuntimeException("failing request")
//            }

            else -> {
                log("car rental succeeded")
                CarRentalResult.SUCCESS
            }
        }
    }

    override fun rollback(cart: CarRentalCart) {
       log("car rental request rolled back")
    }
}
