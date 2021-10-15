package com.khomichenko.sergey.homework1410.subtract

class Subtraction(private val calculationInterface: CalculationInterface) {

    companion object {
        private const val firstNumber = 5f
        private const val secondNumber = 3f
    }

    fun getSubtraction(): Float = calculationInterface.subtraction(firstNumber, secondNumber)

}