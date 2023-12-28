package com.akinci.gymber.ui.ds.components.swipecards.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val id: Int,
    val imageUrl: String,
    val label: String,
) : Parcelable