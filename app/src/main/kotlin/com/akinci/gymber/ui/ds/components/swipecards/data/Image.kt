package com.akinci.gymber.ui.ds.components.swipecards.data

import android.os.Parcelable
import com.akinci.gymber.ui.ds.components.swipecards.SwipeContent
import kotlinx.parcelize.Parcelize

/**
 * Image is data content od [SwipeContent] component
 *
 * **/
@Parcelize
data class Image(
    val id: Int,
    val imageUrl: String,
    val label: String,
) : Parcelable
