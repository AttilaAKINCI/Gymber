package com.akinci.gymber.ui.features.detail

import android.os.Parcelable
import com.akinci.gymber.domain.Gym
import kotlinx.parcelize.Parcelize

object DetailViewContract {

    @Parcelize
    data class ScreenArgs(
        val gym: Gym,
    ) : Parcelable

    data class State(
        val s: String = "",
    )
}