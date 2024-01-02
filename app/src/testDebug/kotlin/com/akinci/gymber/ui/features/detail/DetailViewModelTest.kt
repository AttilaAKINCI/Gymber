package com.akinci.gymber.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.akinci.gymber.R
import com.akinci.gymber.core.coroutine.MainDispatcherRule
import com.akinci.gymber.core.maps.MapsManager
import com.akinci.gymber.domain.Gym
import com.akinci.gymber.domain.Location
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherRule::class)
class DetailViewModelTest {
    private val mapsManagerMock: MapsManager = mockk(relaxed = true)

    private lateinit var testedClass: DetailViewModel

    @BeforeEach
    fun setup() {
        testedClass = DetailViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf("gym" to getGym())
            ),
            mapsManager = mapsManagerMock,
        )
    }

    @Test
    fun `should return parameters when initial state is emitted`() = runTest {
        every { mapsManagerMock.open(any(), any(), any()) } returns Result.success(Unit)
        val expectedGym = getGym()

        testedClass.stateFlow.test {
            with(awaitItem()) {
                snackBarState shouldBe null
                gym shouldBe expectedGym
            }
        }
    }

    @Test
    fun `should return success when google maps app-web is opened`() = runTest {
        val gym = getGym()
        every { mapsManagerMock.open(any(), any(), any()) } returns Result.success(Unit)

        testedClass.stateFlow.test {
            // skip initial state
            skipItems(1)

            testedClass.openGoogleMaps(
                gymName = gym.name,
                location = gym.locations.first()
            )

            expectNoEvents()
        }
    }

    @Test
    fun `should return failure when google maps app-web couldn't opened`() = runTest {
        val gym = getGym()
        every { mapsManagerMock.open(any(), any(), any()) } returns Result.failure(Throwable(""))

        testedClass.stateFlow.test {
            // skip initial state
            skipItems(1)

            testedClass.openGoogleMaps(
                gymName = gym.name,
                location = gym.locations.first()
            )

            with(awaitItem()) {
                snackBarState?.messageId shouldBe R.string.detail_screen_open_map_error
            }
        }
    }

    private fun getGym() = Gym(
        id = 16280,
        name = "Rocycle Amsterdam - City",
        description = "Rocycle maakt van je work-out een feestje op de fiets! In hun boutique sportlocatie fiets je in een met kaarsen verlichte studio, de beats knallen uit de speakers en je traint je bovenlichaam met dumbbells. Ondertussen strooit je instructeur met positieve vibes als confetti. Na 45 minuten knallen verlaat je opgeladen de zaal en kan je jezelf heerlijk terugtrekken in de luxe kleedkamers. Rocycle heeft meerdere prachtige locaties. Let’s party!",
        categories = listOf(
            "Gym",
            "Cycling",
        ),
        facilities = listOf(
            "Airco",
            "Douche",
            "Kleedkamers",
            "Lockers",
        ),
        rating = 4.73,
        reviewCount = 5887,
        dropInEnabled = false,
        reservableWorkouts = true,
        firstComeFirstServe = false,
        imageUrl = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=1680",
        locations = listOf(
            Location(
                latitude = 52.366552,
                longitude = 4.878497,
                city = "Amsterdam",
                postCode = "1016 XP",
                street = "Nieuwe Passeerdersstraat",
                number = "12",
                distance = null,
                distanceText = "",
            )
        )
    )
}
