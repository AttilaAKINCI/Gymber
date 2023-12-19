package com.akinci.gymber.ui.features.dashboard

import com.akinci.gymber.core.compose.UIState

object DashboardScreenViewContract {

    data class State(
        val images: List<String> = emptyList(),
    ): UIState
}