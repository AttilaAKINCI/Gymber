package com.akinci.gymber.ui.features.dashboard

import com.akinci.gymber.core.compose.UIState
import com.akinci.gymber.domain.Gym
import com.akinci.gymber.ui.ds.components.swipecards.data.SwipeImage
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

object DashboardViewContract {

    data class State(
        val isPermissionRequired: Boolean,
        val shouldShowRationale: Boolean = false,

        val gyms: PersistentList<Gym> = persistentListOf(),
        val images: PersistentList<SwipeImage> = persistentListOf(),
    ) : UIState
}
