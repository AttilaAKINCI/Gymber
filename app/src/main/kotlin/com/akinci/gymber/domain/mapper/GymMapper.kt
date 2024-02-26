package com.akinci.gymber.domain.mapper

import com.akinci.gymber.domain.data.Gym
import com.akinci.gymber.ui.ds.components.swipecards.data.Image

fun Gym.toImage() = Image(
    id = id,
    imageUrl = imageUrl,
    label = buildString {
        append(name)

        locations.minByOrNull { it.distance ?: Float.MAX_VALUE }?.let { nearestLocation ->
            val distance = nearestLocation.distanceText
            if (distance.isNotBlank()) {
                append(" - $distance")
            }
        }
    }
)
