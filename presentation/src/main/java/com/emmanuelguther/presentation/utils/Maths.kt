package com.emmanuelguther.presentation.utils

import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.maxDecimals(numFractionDigits: Int) = try {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    (this * factor).roundToInt() / factor
} catch (e: Exception) {
    e.localizedMessage?.toString()?.let { println("Double.maxDecimals: $it") }
    this
}

/**
 * If value is 0.0 returns null
 */
fun Double.isPositive(): Boolean? {
    return when {
        this < 0 ->false
        this > 0 -> true
        else -> null
    }
}
