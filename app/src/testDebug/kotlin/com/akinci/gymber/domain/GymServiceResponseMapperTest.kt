package com.akinci.gymber.domain

import com.akinci.gymber.data.rest.gym.Category
import com.akinci.gymber.data.rest.gym.GymResponse
import com.akinci.gymber.data.rest.gym.GymServiceResponse
import com.akinci.gymber.data.rest.gym.Image
import com.akinci.gymber.data.rest.gym.LocationGroup
import com.akinci.gymber.data.rest.gym.SettlementOption
import com.akinci.gymber.data.mapper.toDomain
import com.akinci.gymber.domain.data.Gym
import com.akinci.gymber.domain.data.Location
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import com.akinci.gymber.data.rest.gym.Location as NetworkLocation

class GymServiceResponseMapperTest {

    @Test
    fun `should map gym service response to domain correctly`() = runTest {
        val expectedDomainModel = getExpectedDomainModel()

        val result = getServiceResponseModel().toDomain()

        result shouldBe expectedDomainModel
    }

    private fun getServiceResponseModel() = GymServiceResponse(
        data = listOf(
            GymResponse(
                id = 16280,
                name = "Rocycle Amsterdam - City",
                description = "Rocycle maakt van je work-out een feestje op de fiets! In hun boutique sportlocatie fiets je in een met kaarsen verlichte studio, de beats knallen uit de speakers en je traint je bovenlichaam met dumbbells. Ondertussen strooit je instructeur met positieve vibes als confetti. Na 45 minuten knallen verlaat je opgeladen de zaal en kan je jezelf heerlijk terugtrekken in de luxe kleedkamers. Rocycle heeft meerdere prachtige locaties. Let’s party!",
                slug = "rocycle-amsterdam-city-amsterdam",
                facilities = listOf(
                    "Airco",
                    "Douche",
                    "Kleedkamers",
                    "Lockers",
                ),
                reviewRating = 4.73,
                reviewCount = 5887,
                isFavorite = false,
                settlementOptions = SettlementOption(
                    dropInEnabled = false,
                    reservableWorkouts = true,
                    firstComeFirstServe = false,
                ),
                distanceFromPosition = 0,
                campaign = true,
                isExclusive = true,
                isNew = false,
                category = Category(id = 1, name = "Gym"),
                categories = listOf(
                    Category(id = 46, name = "Cycling"),
                ),
                headerImage = Image(
                    desktop = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=1680",
                    xxxhdpi = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=1440",
                    xxhdpi = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=1080",
                    tablet = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=1024",
                    mobile = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=768",
                    xhdpi = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=720",
                    hdpi = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=540",
                    mdpi = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=480",
                    ldpi = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=320",
                    thumbnail = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=150",
                    orig = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg"
                ),
                locationGroups = listOf(
                    LocationGroup(
                        latitude = 52.36655199999999,
                        longitude = 4.878497,
                        locations = listOf(
                            NetworkLocation(
                                id = 7302,
                                latitude = 52.366552,
                                longitude = 4.878497,
                                gymId = 16280,
                                streetName = "Nieuwe Passeerdersstraat",
                                houseNumber = "12",
                                addition = "",
                                zipCode = "1016 XP",
                                city = "Amsterdam",
                            )
                        ),
                    )
                ),
                awards = listOf(),
            )
        )
    )

    private fun getExpectedDomainModel() = listOf(
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
        )
    )
}
