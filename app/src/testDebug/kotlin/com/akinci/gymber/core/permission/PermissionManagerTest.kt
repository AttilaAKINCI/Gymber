package com.akinci.gymber.core.permission

import android.content.Context
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PermissionManagerTest {

    private val context = mockk<Context>(relaxed = true)
    private lateinit var spyTestedClass: PermissionManager

    @BeforeEach
    fun setup() {
        spyTestedClass = spyk(PermissionManager(context = context))
    }

    @Test
    fun `should return true for isLocationPermissionGranted when only coarse location permission is provided`() =
        runTest {
            every { spyTestedClass["isGranted"](PermissionManager.COARSE_LOCATION) } returns true
            every { spyTestedClass["isGranted"](PermissionManager.FINE_LOCATION) } returns false

            val isGranted = spyTestedClass.isLocationPermissionGranted()

            isGranted shouldBe true
        }

    @Test
    fun `should return true for isLocationPermissionGranted when only fine location permission is provided`() =
        runTest {
            every { spyTestedClass["isGranted"](PermissionManager.COARSE_LOCATION) } returns false
            every { spyTestedClass["isGranted"](PermissionManager.FINE_LOCATION) } returns true

            val isGranted = spyTestedClass.isLocationPermissionGranted()

            isGranted shouldBe true
        }

    @Test
    fun `should return false for isLocationPermissionGranted when any location permission is not provided`() =
        runTest {
            every { spyTestedClass["isGranted"](PermissionManager.COARSE_LOCATION) } returns false
            every { spyTestedClass["isGranted"](PermissionManager.FINE_LOCATION) } returns false

            val isGranted = spyTestedClass.isLocationPermissionGranted()

            isGranted shouldBe false
        }

    @Test
    fun `should return true for isLocationPermissionGranted when both location permissions are provided`() =
        runTest {
            every { spyTestedClass["isGranted"](PermissionManager.COARSE_LOCATION) } returns true
            every { spyTestedClass["isGranted"](PermissionManager.FINE_LOCATION) } returns true

            val isGranted = spyTestedClass.isLocationPermissionGranted()

            isGranted shouldBe true
        }

    @Test
    fun `should return true for isFineLocationPermissionGranted when permissions is provided`() =
        runTest {
            every { spyTestedClass["isGranted"](PermissionManager.FINE_LOCATION) } returns true

            val isGranted = spyTestedClass.isFineLocationPermissionGranted()

            isGranted shouldBe true
        }

    @Test
    fun `should return false for isFineLocationPermissionGranted when permissions is provided`() =
        runTest {
            every { spyTestedClass["isGranted"](PermissionManager.FINE_LOCATION) } returns false

            val isGranted = spyTestedClass.isFineLocationPermissionGranted()

            isGranted shouldBe false
        }
}
