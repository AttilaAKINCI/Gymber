package com.akinci.gymber.ui.features.dashboard

import androidx.annotation.StringRes
import com.akinci.gymber.domain.data.Gym
import com.akinci.gymber.ui.ds.components.swipecards.data.Image
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

object DashboardViewContract {

    data class State(
        val isErrorDialogVisible: Boolean = false,
        val isRationaleDialogVisible: Boolean = false,
        val isMatchOverlayVisible: Boolean = false,

        val isDistanceCalculated: Boolean = false,

        val gyms: List<Gym> = listOf(),
        val imageStates: PersistentList<Image> = persistentListOf(),
        val matchedGym: Gym? = null,
    ) {
        fun getGym(id: Int) = gyms.firstOrNull { it.id == id }
    }

    sealed interface Action {
        data object OnPermissionGranted : Action
        data object ShowRationaleDialog : Action
        data object HideRationaleDialog : Action
        data class OnDetailButtonClick(val gymId: Int) : Action
        data class OnLikeButtonClick(val gymId: Int) : Action
        data class OnDislikeButtonClick(val gymId: Int) : Action
        data object OnRefreshButtonClick : Action
        data object OnDismissMatchButtonClick : Action
        data object OnCallMatchButtonClick : Action
        data object OnMessageMatchButtonClick : Action
    }

    sealed interface Effect {
        data class OpenDetailScreen(val gym: Gym) : Effect
        data object RequestPermission : Effect
        data class ShowToastMessage(@StringRes val messageId: Int) : Effect
    }
}
