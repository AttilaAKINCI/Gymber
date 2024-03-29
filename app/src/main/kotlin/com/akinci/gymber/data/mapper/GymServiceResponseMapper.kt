package com.akinci.gymber.data.mapper

import com.akinci.gymber.data.rest.gym.GymServiceResponse
import com.akinci.gymber.domain.data.Gym

fun GymServiceResponse.toDomain() = data.map {
    Gym(
        id = it.id,
        name = it.name,
        description = it.description,
        categories = buildList {
            add(it.category.name)
            addAll(it.categories.map { category -> category.name })
        },
        facilities = it.facilities,
        rating = it.reviewRating,
        reviewCount = it.reviewCount,
        dropInEnabled = it.settlementOptions.dropInEnabled,
        reservableWorkouts = it.settlementOptions.reservableWorkouts,
        firstComeFirstServe = it.settlementOptions.firstComeFirstServe,
        imageUrl = it.headerImage.desktop,
        locations = it.locationGroups.toDomain(),
    )
}