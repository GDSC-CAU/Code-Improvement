internal interface EngineControl {
    @kotlin.Throws(PowerExecption::class)
    fun on()
    fun off()

    @kotlin.Throws(EngineError::class)
    fun powerUp()

    @kotlin.Throws(EngineError::class)
    fun powerDown()
}

class Fan(val maxLevel: Int): EngineControl {
    var curLevel = 0
    var isOn = false
    var isRotate = false

    @kotlin.Throws(PowerExecption::class)
    fun on() {
        if (!isOn) {
            curLevel = 1
            isOn = true
        } else {
            throw PowerException("Fan is Already On")
        }
    }

    fun off() {
        if (isOn) {
            curLevel = 0
            isOn = false
        } else {
            throw PowerException("Fan is Not Powered On")
        }
    }

    @kotlin.Throws(EngineError::class)
    fun powerUp() {
        if (!isOn) {
            throw EngineError("Engine is Not Powered On")
        }

        if (curLevel < maxLevel - 1) {
            curLevel++
        } else {
            throw EngineError("Engine is Already Max Level")
        }
    }

    @kotlin.Throws(EngineError::class)
    fun powerDown() {
        if (!isOn) {
            throw EngineError("Engine is Not Powered On")
        }

        if (curLevel > 1) {
            curLevel--
        } else {
            off()
            throw EngineError("Engine was Level 1, so Powered Off")
        }
    }

    fun toggleRotate() {
        isRotate = !isRotate
    }
}
