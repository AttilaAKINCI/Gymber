package com.akinci.gymber.data.mapper

import com.akinci.gymber.data.remote.GymServiceResponse
import com.akinci.gymber.domain.Gym

fun GymServiceResponse.toDomain() = data.map {
    Gym(
        id = it.id,
        name = it.name,
        description = it.description,
        facilities = it.facilities,
        rating = it.reviewRating,
        reviewCount = it.reviewCount,
        dropInEnabled = it.settlementOptions.dropInEnabled,
        reservableWorkouts = it.settlementOptions.reservableWorkouts,
        firstComeFirstServe = it.settlementOptions.firstComeFirstServe,
        imageUrl = it.headerImage.desktop,
        locations = it.locationGroups.toDomain(),
        distance = "",
    )
}