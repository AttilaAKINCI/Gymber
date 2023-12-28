package com.akinci.gymber.ui.ds.components.swipecards.data

import androidx.annotation.DrawableRes
import com.akinci.gymber.ui.ds.components.swipecards.SwipeBox

/**
 * [SwipeBox] component is capable of managing 4 action buttons.
 * Icons of these buttons set by below fields.
 * **/
data class ActionButtons(
    @DrawableRes val detailIcon: Int,
    @DrawableRes val approveIcon: Int,
    @DrawableRes val rejectIcon: Int,
    @DrawableRes val reverseIcon: Int,
)