package com.akinci.gymber.data.mapper

import com.akinci.gymber.domain.Location
import com.akinci.gymber.data.remote.LocationGroup as NetworkLocationGroup

fun List<NetworkLocationGroup>.toDomain() = map { locationGroup ->
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


