package com.akinci.gymber.ui.ds.components.swipecards.data

import androidx.annotation.DrawableRes
import com.akinci.gymber.ui.ds.components.swipecards.SwipeBox

/**
 * ActionButtons holds resourceIds of [SwipeBox] component's action buttons.
 *
 * **/
data class ActionButtons(
    @DrawableRes val detailIcon: Int,
    @DrawableRes val approveIcon: Int,
    @DrawableRes val rejectIcon: Int,
    @DrawableRes val reverseIcon: Int,
)
