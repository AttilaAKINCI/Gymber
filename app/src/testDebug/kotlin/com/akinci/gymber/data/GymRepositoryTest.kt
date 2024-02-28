package com.akinci.gymber.data

import com.akinci.gymber.core.application.AppConfig
import com.akinci.gymber.core.network.HttpClientFactory
import com.akinci.gymber.core.network.HttpEngineFactoryMock
import com.akinci.gymber.data.repository.GymRepository
import com.akinci.gymber.domain.data.Gym
import com.akinci.gymber.domain.data.Location
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpStatusCode
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GymRepositoryTest {

    private val appConfigMock: AppConfig = mockk(relaxed = true)

    private val httpEngineFactory = HttpEngineFactoryMock()
    private val httpClientFactory = HttpClientFactory(
        httpEngineFactory = httpEngineFactory,
        appConfig = appConfigMock,
    )

    private lateinit var testedClass: GymRepository

    @BeforeEach
    fun setup() {
        testedClass = GymRepository(
            httpClient = httpClientFactory.create()
        )
    }

    @Test
    fun `should return gyms when response is successful`() = runTest {
        httpEngineFactory.statusCode = HttpStatusCode.OK
        val expectedResult = getGymListResponse()

        val result = testedClass.getGyms()

        result.isSuccess shouldBe true
        result.getOrNull()!! shouldBe expectedResult
    }

    @Test
    fun `should throw exception when response is not successful`() = runTest {
        httpEngineFactory.statusCode = HttpStatusCode.BadGateway

        val result = testedClass.getGyms()

        result.isFailure shouldBe true
        result.exceptionOrNull()!! shouldBe Throwable("Bad Gateway")
    }

    private fun getGymListResponse() = listOf(
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
