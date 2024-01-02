package com.akinci.gymber.ui.ds.components.swipecards.data

import com.akinci.gymber.ui.ds.components.swipecards.SwipeImage
import com.akinci.gymber.ui.ds.components.swipecards.SwipeBox

/**
 * Position type of [SwipeImage]
 * [SwipeBox] component holds [SwipeImage] at center (for current) and at the side (for reverse).
 *
 * **/
enum class Type {
    CENTER,
    REVERSE,
}
