package com.akinci.gymber.ui.features.dashboard

import com.akinci.gymber.core.compose.UIState
import com.akinci.gymber.domain.Gym
import com.akinci.gymber.ui.ds.components.swipecards.data.Image
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

object DashboardViewContract {

    data class State(
        val isPermissionRequired: Boolean,
        val shouldShowRationale: Boolean = false,
        val isDistanceCalculated: Boolean = false,

        val isError: Boolean = false,
        val gyms: List<Gym> = listOf(),
        val imageStates: PersistentList<Image> = persistentListOf(),
    ) : UIState
}
