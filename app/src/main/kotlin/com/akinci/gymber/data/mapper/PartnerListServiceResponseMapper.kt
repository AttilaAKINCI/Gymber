package com.akinci.gymber.data.mapper

import com.akinci.gymber.data.remote.PartnerListServiceResponse
import com.akinci.gymber.domain.Partner

fun PartnerListServiceResponse.toDomain() = data.map {
    Partner(
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