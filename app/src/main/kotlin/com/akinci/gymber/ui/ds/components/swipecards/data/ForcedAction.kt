package com.akinci.gymber.ui.ds.components.swipecards.data

data class ForcedAction(
    val swipeLeft: Int = 0,
    val swipeRight: Int = 0,
) {
    fun swipeLeft() = copy(swipeLeft = swipeLeft.inc())

    fun swipeRight() = copy(swipeRight = swipeRight.inc())
}
