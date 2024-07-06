package loyalty.workflows

import io.infinitic.annotations.Name

@Name("Loyalty")
interface Loyalty {
    val points: Int?

    fun start()

    fun addBonus(event: BonusEvent)
}
