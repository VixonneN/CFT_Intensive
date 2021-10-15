package com.khomichenko.sergey.homework1410

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatedUnitTest {

    private val calculator = CalculatedViewModel()

    @Test
    fun `WHEN multiply 2 by 4 EXPECT 8`() {

        val actual = calculator.multiply(2f, 4f)
        val expected = 8f

        assertEquals(expected, actual)
    }

    @Test
    fun `WHEN logarithm 10 EXPECT 1` (){
        val actual = calculator.logarithm(10.0)
        val expected = 1.0
        val delta = 0.0

        assertEquals(expected, actual, delta)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `When divide by zero EXPECT IllegalArgumentException`(){
        calculator.divide(8f, 0f)
    }


}