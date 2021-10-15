package com.khomichenko.sergey.homework1410

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.kotlin.mock

@RunWith(Parameterized::class)
class CalculatedSquareRootParameterizedTest(
    private val value: Double,
    private val expected: Double,
    private val delta: Double) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = arrayOf(
            arrayOf(4.0, 2.0, 0.0),
            arrayOf(9.0, 3.0, 0.0),
            arrayOf(400.0, 20.0, 0.0)
        )
    }

    @Test
    fun `WHEN sqrt EXPECT correct result`() {
        val calculator = CalculatedViewModel()

        val actual = calculator.squareRoot(value)

        assertEquals(expected, actual, delta)
    }
}