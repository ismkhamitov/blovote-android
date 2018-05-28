package com.blovote.surveys.ui

import java.math.BigInteger

fun BigInteger.toReadableString() : String {
    if (this < BigInteger.TEN.pow(3)) {
        return String.format("%s wei", this)
    } else {
        val value : Double
        val unit : String
        when {
            this < BigInteger.TEN.pow(6) -> {
                value = this.divide(BigInteger.TEN.pow(3)).toDouble()
                unit = "Kwei"
            }
            this < BigInteger.TEN.pow(9) -> {
                value = this.divide(BigInteger.TEN.pow(6)).toDouble()
                unit = "Mwei"
            }
            this < BigInteger.TEN.pow(12) -> {
                value = this.divide(BigInteger.TEN.pow(9)).toDouble()
                unit = "Gwei"
            }
            this < BigInteger.TEN.pow(15) -> {
                value = this.divide(BigInteger.TEN.pow(12)).toDouble()
                unit = "microether"
            }
            this < BigInteger.TEN.pow(18) -> {
                value = this.divide(BigInteger.TEN.pow(15)).toDouble()
                unit = "milliether"
            }
            else -> {
                value = this.divide(BigInteger.TEN.pow(18)).toDouble()
                unit = "ether"
            }
        }

        return String.format("%.3f %s", value, unit)
    }
}