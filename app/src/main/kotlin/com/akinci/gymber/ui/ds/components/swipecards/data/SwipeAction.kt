package com.akinci.gymber.ui.ds.components.swipecards.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID
import com.akinci.gymber.ui.ds.components.swipecards.SwipeImage

/**
 * [SwipeImage] component is capable of automatic swipe actions via action buttons.
 * SwipeAction class delivers that state to [SwipeImage]
 * **/
@Parcelize
data class SwipeAction(
    val actionId: UUID = UUID.randomUUID(),
    val direction: Direction? = null,
) : Parcelable