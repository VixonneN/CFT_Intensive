package com.khomichenko.sergey.homework1410

import com.khomichenko.sergey.homework1410.subtract.CalculationInterface
import com.khomichenko.sergey.homework1410.subtract.Subtraction
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SubtractionTest {

    @Test
    fun `WHEN subtractions GET right value`(){
        val testSubtraction: CalculationInterface = mock()
        whenever(testSubtraction.subtraction(5f, 3f)).thenReturn(2f)

        val ggt = Subtraction(testSubtraction)

        val actual = ggt.getSubtraction()

        val expect = 2f

        assertEquals(expect, actual)
    }
}