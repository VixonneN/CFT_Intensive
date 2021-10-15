package com.khomichenko.sergey.homework1410

import java.lang.Math.log10
import java.lang.Math.sqrt


class CalculatedViewModel {

    fun multiply(multiplicand: Float, multiplier: Float): Float =
        multiplicand * multiplier


    fun divide(dividend: Float, divisor: Float): Float =
        if (divisor != 0f) {
            dividend / divisor
        } else {
            throw IllegalArgumentException("Divisor cannot be 0")
        }

    fun logarithm(x: Double): Double =
        log10(x)

    fun squareRoot(x: Double): Double =
        sqrt(x)

}