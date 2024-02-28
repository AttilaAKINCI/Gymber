package com.akinci.gymber.ui.features.dashboard

import app.cash.turbine.test
import com.akinci.gymber.R
import com.akinci.gymber.core.coroutine.MainDispatcherRule
import com.akinci.gymber.core.coroutine.TestContextProvider
import com.akinci.gymber.core.location.Coordinate
import com.akinci.gymber.core.location.LocationManager
import com.akinci.gymber.core.permission.PermissionManager
import com.akinci.gymber.core.utils.GymMatchSimulator
import com.akinci.gymber.core.utils.distance.Distance
import com.akinci.gymber.core.utils.distance.DistanceUtils
import com.akinci.gymber.data.repository.GymRepository
import com.akinci.gymber.domain.data.Gym
import com.akinci.gymber.domain.data.Location
import com.akinci.gymber.ui.features.dashboard.DashboardViewContract.Action
import com.akinci.gymber.ui.features.dashboard.DashboardViewContract.Effect
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherRule::class)
class DashboardViewModelTest {
    private val testContextProvider = TestContextProvider()
    private val permissionManagerMock: PermissionManager = mockk(relaxed = true)
    private val gymRepositoryMock: GymRepository = mockk(relaxed = true)
    private val locationManagerMock: LocationManager = mockk(relaxed = true)
    private val distanceUtilsMock: DistanceUtils = mockk(relaxed = true)
    private val gymMatchSimulator: GymMatchSimulator = mockk(relaxed = true)

    private lateinit var testedClass: DashboardViewModel

    @BeforeEach
    fun setup() {
        // default mocks
        every { permissionManagerMock.isLocationPermissionGranted() } returns true
        every { gymMatchSimulator.isItAMatch() } returns false
        coEvery { gymRepositoryMock.getGyms() } returns Result.success(getGyms())
        coEvery { locationManagerMock.getCurrentLocation() } returns Result.success(
            getCurrentDeviceLocation()
        )
        every { distanceUtilsMock.calculateDistance(any(), any()) } returns Distance(
            value = 100f,
            valueText = "100 m"
        )

        initTestClass()
    }

    @Test
    fun `should return error when initial service call is failed`() = runTest {
        coEvery { gymRepositoryMock.getGyms() } returns Result.failure(Throwable())

        initTestClass()

        testedClass.state.test {
            with(awaitItem()) {
                isRationaleDialogVisible shouldBe false
                isDistanceCalculated shouldBe false
                isMatchOverlayVisible shouldBe false
                isErrorDialogVisible shouldBe true
                gyms shouldBe listOf()
                imageStates shouldBe persistentListOf()
                matchedGym shouldBe null
            }
        }
    }

    @Test
    fun `should return gyms with calculated distance when device location is known`() = runTest {
        testedClass.state.test {
            with(awaitItem()) {
                isRationaleDialogVisible shouldBe false
                isDistanceCalculated shouldBe true
                isErrorDialogVisible shouldBe false
                gyms.size shouldBeGreaterThan 0
                gyms.all { gym ->
                    gym.locations.all { it.distance != null && it.distanceText.isNotBlank() }
                } shouldBe true
                imageStates.size shouldBeGreaterThan 0
                isMatchOverlayVisible shouldBe false
                matchedGym shouldBe null
            }
        }
    }

    @Test
    fun `should return gyms with empty distance when device location is unknown`() = runTest {
        coEvery { locationManagerMock.getCurrentLocation() } returns Result.failure(Throwable())

        initTestClass()

        testedClass.state.test {
            with(awaitItem()) {
                isRationaleDialogVisible shouldBe false
                isDistanceCalculated shouldBe false
                isErrorDialogVisible shouldBe false
                gyms.size shouldBeGreaterThan 0
                imageStates.size shouldBeGreaterThan 0
                gyms.all { gym ->
                    gym.locations.all { it.distance == null && it.distanceText.isBlank() }
                } shouldBe true
                isMatchOverlayVisible shouldBe false
                matchedGym shouldBe null
            }
        }
    }

    @Test
    fun `should send initial loading state when refreshed`() = runTest {

        testedClass.refresh()

        testedClass.state.test {
            with(awaitItem()) {
                isRationaleDialogVisible shouldBe false
                isDistanceCalculated shouldBe true
                isErrorDialogVisible shouldBe false
                gyms.size shouldBe 0
                imageStates.size shouldBe 0
                isMatchOverlayVisible shouldBe false
                matchedGym shouldBe null
            }
        }
    }

    @Test
    fun `should send correct message on match when message action selected`() = runTest {
        testedClass.messageMatch()

        testedClass.effect.test {
            with(awaitItem()) {
                assert(this is Effect.ShowToastMessage)
                (this as Effect.ShowToastMessage).messageId shouldBe R.string.dashboard_screen_match_message_warning
            }
        }
    }

    @Test
    fun `should send correct message on match when call action selected`() = runTest {
        testedClass.callMatch()

        testedClass.effect.test {
            with(awaitItem()) {
                assert(this is Effect.ShowToastMessage)
                (this as Effect.ShowToastMessage).messageId shouldBe R.string.dashboard_screen_match_call_warning
            }
        }
    }

    @Test
    fun `should send isMatchOverlayVisible false when skip match action`() = runTest {
        testedClass.dismissMatch()

        testedClass.state.test {
            with(awaitItem()) {
                isMatchOverlayVisible shouldBe false
            }
        }
    }

    @Test
    fun `should set matched gym when swipe is a match`() = runTest {
        every { gymMatchSimulator.isItAMatch() } returns true

        testedClass.onGymLike(16280)

        testedClass.state.test {
            with(awaitItem()) {
                isMatchOverlayVisible shouldBe true
                matchedGym shouldBe gyms.firstOrNull { it.id == 16280 }
            }
        }
    }

    @Test
    fun `should hide permission rationale via state dialog when user click close button`() =
        runTest {

            testedClass.hideRationaleDialog()

            testedClass.state.test {
                with(awaitItem()) {
                    isRationaleDialogVisible shouldBe false
                }
            }
        }

    @Test
    fun `should return gyms with calculated distance after permission request is approved`() =
        runTest {
            every { permissionManagerMock.isLocationPermissionGranted() } returns false
            coEvery { locationManagerMock.getCurrentLocation() } returns Result.failure(Throwable())

            initTestClass()

            testedClass.state.test {
                // skip initial state
                skipItems(1)

                coEvery { locationManagerMock.getCurrentLocation() } returns Result.success(
                    getCurrentDeviceLocation()
                )

                // location permission request is approved
                testedClass.onAction(Action.OnPermissionGranted)

                with(awaitItem()) {
                    isDistanceCalculated shouldBe true
                    isErrorDialogVisible shouldBe false
                    gyms.size shouldBeGreaterThan 0
                    imageStates.size shouldBeGreaterThan 0
                    gyms.all { gym ->
                        gym.locations.all { it.distance != null && it.distanceText.isNotBlank() }
                    } shouldBe true
                }

                expectNoEvents()
            }
        }

    private fun initTestClass() {
        testedClass = DashboardViewModel(
            permissionManager = permissionManagerMock,
            contextProvider = testContextProvider,
            gymRepository = gymRepositoryMock,
            locationManager = locationManagerMock,
            distanceUtils = distanceUtilsMock,
            gymMatchSimulator = gymMatchSimulator,
        )
    }

    private fun getCurrentDeviceLocation() = Coordinate(
        latitude = 51.37154734,
        longitude = 3.87995458,
    )

    private fun getGyms() = listOf(
        Gym(
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
        ),
        Gym(
            id = 17173,
            name = "Double Shift",
            description = "Double Shift is meer dan een gym. Ervaar een complete 360 gym experience in deze oude suikerfabriek. Plof na het sporten neer in de lounge en geniet van een heerlijk kopje espresso. Alle groepslessen worden gegeven door ervaren en enthousiaste trainers. Je kunt hier terecht voor vrije training, enorm veel workouts en vooral veel Sweat and Joy!",
            categories = listOf(
                "Gym",
                "Overig",
                "Fitness",
            ),
            facilities = listOf(
                "Airco",
                "Café / lounge",
                "Douche",
                "Kleedkamers",
                "Sauna",
                "WiFi"
            ),
            rating = 4.88,
            reviewCount = 319,
            dropInEnabled = true,
            reservableWorkouts = true,
            firstComeFirstServe = false,
            imageUrl = "https://edge.one.fit/image/partner/image/17173/2a602be6-d7f1-4922-b669-5df0bb547191.jpg?w=1680",
            locations = listOf(
                Location(
                    latitude = 52.37154734,
                    longitude = 4.87995458,
                    city = "Amsterdam",
                    postCode = "1016 RP",
                    street = "Lauriergracht",
                    number = "102",
                    distance = null,
                    distanceText = "",
                ),
                Location(
                    latitude = 52.37133319,
                    longitude = 4.88001107,
                    city = "Amsterdam",
                    postCode = "1016",
                    street = "Lauriergracht",
                    number = "102",
                    distance = null,
                    distanceText = "",
                )
            ),
        )
    )
}