package com.akinci.gymber.ui.ds.components.swipecards.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class SwipeAction(
    val actionId: UUID = UUID.randomUUID(),
    val direction: Direction? = null,
) : Parcelable