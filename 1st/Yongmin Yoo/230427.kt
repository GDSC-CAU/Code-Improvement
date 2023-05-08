import kotlin.math.sqrt

class Solution {
    fun bulbSwitch(n: Int): Int {
        return sqrt(n.toDouble()).toInt()
    }
}
