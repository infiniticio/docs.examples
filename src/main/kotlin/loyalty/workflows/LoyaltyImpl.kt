package loyalty.workflows

import io.infinitic.workflows.Workflow
import java.time.Instant

class LoyaltyImpl : Workflow(), Loyalty {
    // private val weekInSeconds = 3600*24*7;
    private val weekInSeconds = 4

    override var points = 0

    override fun start() {
        val now = inline { Instant.now() }

        var w = 0
        while (w < 56) {
            inline { println("points = $points") }

            // every week, a new point is added
            w++
            timer(now.plusSeconds((w * weekInSeconds).toLong())).await()
            points++
        }
    }

    override fun addBonus(event: BonusEvent) {
        inline { println("received $event") }

        points += when (event) {
            BonusEvent.REGISTRATION_COMPLETED -> 100
            BonusEvent.FORM_COMPLETED -> 200
            BonusEvent.ORDER_COMPLETED -> 500
        }
    }
}