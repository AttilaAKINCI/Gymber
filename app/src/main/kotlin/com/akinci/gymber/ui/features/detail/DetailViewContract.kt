package com.akinci.gymber.ui.features.detail

import android.os.Parcelable
import com.akinci.gymber.core.compose.UIState
import com.akinci.gymber.domain.Gym
import com.akinci.gymber.ui.ds.components.snackbar.SnackBarState
import kotlinx.parcelize.Parcelize

object DetailViewContract {

    @Parcelize
    data class ScreenArgs(
        val gym: Gym,
    ) : Parcelable

    data class State(
        val gym: Gym,
        val snackBarState: SnackBarState? = null,
    ) : UIState
}