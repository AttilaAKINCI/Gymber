package com.akinci.gymber.ui.features.splash

import app.cash.turbine.test
import com.akinci.gymber.core.coroutine.MainDispatcherRule
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.time.Duration

@ExtendWith(MainDispatcherRule::class)
class SplashViewModelTest {

    private lateinit var testedClass: SplashViewModel

    @BeforeEach
    fun setup() {
        testedClass = SplashViewModel()
    }

    @Test
    fun `should return completed state when initialisation is done`() = runTest {
        val expectedState = SplashViewContract.State(
            isCompleted = true
        )

        testedClass.stateFlow.test(timeout = Duration.INFINITE) {
            skipItems(1)

            awaitItem() shouldBe expectedState

            ensureAllEventsConsumed()
        }
    }
}
