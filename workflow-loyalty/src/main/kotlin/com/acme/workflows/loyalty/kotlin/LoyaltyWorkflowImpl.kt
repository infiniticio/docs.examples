package com.acme.workflows.loyalty.kotlin

import com.acme.utils.AbstractWorkflow
import com.acme.workflows.loyaty.BonusEvent
import com.acme.workflows.loyaty.LoyaltyWorkflow
import java.time.Duration

class LoyaltyWorkflowImpl : AbstractWorkflow(), LoyaltyWorkflow {
    private val self = getWorkflowById(LoyaltyWorkflow::class.java, workflowId)

    private val secondsForPointReward: Long = 10

    private var points: Long = 0

    override fun getPoints() = points

    override fun start() {
        log("points = $points")

        // every `secondsForPointReward` seconds, a new point is added
        timer(Duration.ofSeconds(secondsForPointReward)).await()
        points++

        // Loop
        dispatch(self::start)
    }

    override fun addBonus(event: BonusEvent) {
        points += when (event) {
            BonusEvent.REGISTRATION_COMPLETED -> 100
            BonusEvent.FORM_COMPLETED -> 200
            BonusEvent.ORDER_COMPLETED -> 500
        }
        log("received $event - new points = $points")
    }
}
