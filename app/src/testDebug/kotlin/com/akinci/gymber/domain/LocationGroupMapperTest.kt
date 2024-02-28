package com.akinci.gymber.domain

import com.akinci.gymber.data.rest.gym.Location
import com.akinci.gymber.data.rest.gym.LocationGroup
import com.akinci.gymber.data.mapper.toDomain
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class LocationGroupMapperTest {

    @Test
    fun `should map location groups to domain correctly`() = runTest {
        val expectedDomainModel = getExpectedDomainModel()

        val result = getServiceResponseModel().toDomain()

        result shouldBe expectedDomainModel
    }

    private fun getExpectedDomainModel() = listOf(
        com.akinci.gymber.domain.data.Location(
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

    private fun getServiceResponseModel() = listOf(
        LocationGroup(
            latitude = 52.36655199999999,
            longitude = 4.878497,
            locations = listOf(
                Location(
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
    )
}