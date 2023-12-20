package com.akinci.gymber.ui.features.dashboard

import com.akinci.gymber.core.compose.UIState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

object DashboardScreenViewContract {

    data class State(
        val images: PersistentList<String> = persistentListOf(),
    ): UIState
}