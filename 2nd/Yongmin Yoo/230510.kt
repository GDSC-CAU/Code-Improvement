internal interface EngineControl {
    @kotlin.Throws(PowerExecption::class)
    fun on()
    fun off()

    @kotlin.Throws(EngineError::class)
    fun powerUp()

    @kotlin.Throws(EngineError::class)
    fun powerDown()
}
