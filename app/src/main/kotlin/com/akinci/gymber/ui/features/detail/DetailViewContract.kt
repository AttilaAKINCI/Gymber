package com.akinci.gymber.ui.features.detail

import android.os.Parcelable
import androidx.annotation.StringRes
import com.akinci.gymber.domain.data.Gym
import com.akinci.gymber.domain.data.Location
import kotlinx.parcelize.Parcelize

object DetailViewContract {

    @Parcelize
    data class ScreenArgs(
        val gym: Gym,
    ) : Parcelable

    data class State(
        val gym: Gym,
    )

    sealed interface Action {
        data object OnBackPressed : Action
        data class OpenMaps(
            val location: Location,
            val gymName: String,
        ) : Action
    }

    sealed interface Effect {
        data object Close : Effect
        data class ShowToastMessage(@StringRes val messageId: Int): Effect
    }
}
