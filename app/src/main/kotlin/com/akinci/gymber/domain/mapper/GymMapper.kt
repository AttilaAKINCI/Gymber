package com.akinci.gymber.domain.mapper

import com.akinci.gymber.domain.Gym
import com.akinci.gymber.domain.getNearest
import com.akinci.gymber.ui.ds.components.swipecards.data.Image

fun Gym.toImage() = Image(
    id = id,
    imageUrl = imageUrl,
    label = buildString {
        append(name)
        locations.getNearest()?.distanceText?.let {
            append(" - $it")
        }
    }
)

fun List<Gym>.toImages() = map { it.toImage() }
fun List<Gym>.getImage(index: Int) = runCatching { get(index).toImage() }.getOrNull()
