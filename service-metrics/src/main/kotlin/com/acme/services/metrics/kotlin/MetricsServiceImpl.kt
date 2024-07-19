package com.acme.services.metrics.kotlin

import com.acme.services.metrics.MetricsService
import com.acme.utils.AbstractService
import com.acme.workflows.invoicing.User
import java.time.Duration
import java.time.LocalDateTime

class MetricsServiceImpl: AbstractService(), MetricsService {

    override fun calculateAmount(user: User, start: LocalDateTime?, end: LocalDateTime?): Double {
        val amount = Duration.between(start, end).toMillis() * Math.random()
        log("Amount calculated for user(${user.id}), from $start to $end: $amount")
        return amount
    }

    override fun isSubscribed(user: User): Boolean {
        val isSubscribed = true;
        log("Checking if user (${user.id}) is still subscribed... $isSubscribed")
        return isSubscribed
    }
}