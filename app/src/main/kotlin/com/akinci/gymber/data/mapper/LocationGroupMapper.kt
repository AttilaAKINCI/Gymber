package com.akinci.gymber.data.mapper

import com.akinci.gymber.data.rest.gym.LocationGroup
import com.akinci.gymber.domain.data.Location

fun List<LocationGroup>.toDomain() = map { locationGroup ->
    locationGroup.locations.map {
        Location(
            latitude = it.latitude,
            longitude = it.longitude,
            city = it.city,
            postCode = it.zipCode,
            street = it.streetName,
            number = it.houseNumber,
        )
    }
}.flatten()


