package com.akinci.gymber.core.utils

import com.akinci.gymber.core.location.Coordinate
import com.akinci.gymber.core.utils.distance.Distance
import com.akinci.gymber.core.utils.distance.DistanceCalculator
import com.akinci.gymber.core.utils.distance.DistanceUtils
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DistanceUtilsTest {

    private val distanceCalculatorMock: DistanceCalculator = mockk(relaxed = true)

    private lateinit var testedClass: DistanceUtils

    @BeforeEach
    fun setup() {
        testedClass = DistanceUtils(
            distanceCalculator = distanceCalculatorMock
        )
    }

    @Test
    fun `should return km adjusted distance`() = runTest {
        every { distanceCalculatorMock.calculateFlightDistance(any(), any()) } returns 1200f

        val from = Coordinate(latitude = 54.39876, longitude = 4.8766634)
        val to = Coordinate(latitude = 54.45876, longitude = 4.9966634)
        val expectedDistance = Distance(
            value = 1200f,
            valueText = "1.2 km"
        )

        val distance = testedClass.calculateDistance(from, to)

        distance shouldBe expectedDistance
    }

    @Test
    fun `should return m adjusted distance`() = runTest {
        every { distanceCalculatorMock.calculateFlightDistance(any(), any()) } returns 800f

        val from = Coordinate(latitude = 54.39876, longitude = 4.8766634)
        val to = Coordinate(latitude = 54.45876, longitude = 4.9966634)
        val expectedDistance = Distance(
            value = 800f,
            valueText = "800 m"
        )

        val distance = testedClass.calculateDistance(from, to)

        distance shouldBe expectedDistance
    }
}
