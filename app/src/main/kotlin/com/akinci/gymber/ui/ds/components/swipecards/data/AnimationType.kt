package com.akinci.gymber.ui.ds.components.swipecards.data

import com.akinci.gymber.ui.ds.components.swipecards.SwipeImage

/**
 * Swipe animation type of [SwipeImage] component's actions.
 *
 * @property [duration] animation duration
 *
 * **/
enum class AnimationType(
    val duration: Int
) {
    SOFT(0),
    ANIMATED(400),
    INSTANT(0),
}
