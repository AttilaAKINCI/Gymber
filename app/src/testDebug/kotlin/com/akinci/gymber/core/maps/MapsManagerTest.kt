package com.akinci.gymber.core.maps

import android.content.Context
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import timber.log.Timber

class MapsManagerTest {

    private val context = mockk<Context>(relaxed = true)
    private lateinit var spykTestedClass: MapsManager

    @BeforeEach
    fun setup() {
        mockkObject(Timber)
        spykTestedClass = spyk(MapsManager(context = context), recordPrivateCalls = true)
    }

    @Test
    fun `should return success when google map or web is opened`() = runTest {
        val result = spykTestedClass.open(
            latitude = 54.98233,
            longitude = 4.974534,
            name = "Rocycle Amsterdam",
        )

        result.isSuccess shouldBe true
        verify(exactly = 1) {
            spykTestedClass["buildAppIntent"](any() as Double, any() as Double, any() as String)
        }
        verify(exactly = 0) {
            spykTestedClass["buildWebIntent"](any() as Double, any() as Double)
        }
    }

    @Test
    fun `should log an error and use fallback when request is failed during google maps app launch`() =
        runTest {
            val throwable = Throwable("App launch exception")
            every { context.startActivity(any()) } throws throwable

            spykTestedClass.open(
                latitude = 54.98233,
                longitude = 4.974534,
                name = "Rocycle Amsterdam",
            )

            verify(exactly = 1) { Timber.e(throwable, "Unable to open GoogleMaps App") }
            verify(exactly = 1) {
                spykTestedClass["buildAppIntent"](any() as Double, any() as Double, any() as String)
            }
            verify(exactly = 1) {
                spykTestedClass["buildWebIntent"](any() as Double, any() as Double)
            }
        }
}
