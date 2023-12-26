package com.akinci.gymber.domain.mapper

import com.akinci.gymber.data.remote.LocationGroup
import com.akinci.gymber.domain.Location

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


