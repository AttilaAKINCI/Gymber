package com.akinci.gymber.domain

import com.akinci.gymber.domain.mapper.toImage
import com.akinci.gymber.ui.ds.components.swipecards.data.Image
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class GymMapperTest {

    @Test
    fun `should map gym to image when distance is unknown`() = runTest {
        val gym = getDomainModelWithoutDistance()
        val expectedImageModel = Image(
            id = 16280,
            imageUrl = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=1680",
            label = "Rocycle Amsterdam - City",
        )

        val image = gym.toImage()

        image shouldBe expectedImageModel
    }

    @Test
    fun `should map gym to image when distance is calculated`() = runTest {
        val gym = getDomainModelWithCalculatedDistance()
        val expectedImageModel = Image(
            id = 16280,
            imageUrl = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=1680",
            label = "Rocycle Amsterdam - City - 100 m",
        )

        val image = gym.toImage()

        image shouldBe expectedImageModel
    }

    private fun getDomainModelWithoutDistance() = Gym(
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

    private fun getDomainModelWithCalculatedDistance() = Gym(
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
                distance = 100f,
                distanceText = "100 m",
            )
        )
    )
}
