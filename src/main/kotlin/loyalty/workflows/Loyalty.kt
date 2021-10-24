package loyalty.workflows

interface Loyalty {
    val points: Int?

    fun start()

    fun addBonus(event: BonusEvent)
}