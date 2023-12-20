package com.akinci.gymber.ui.features.dashboard

import com.akinci.gymber.core.compose.UIState
import com.akinci.gymber.domain.Image
import com.akinci.gymber.domain.Partner

object DashboardScreenViewContract {

    data class State(
        val partners: List<Partner> = listOf(),
        val images: List<Image> = listOf(),
    ) : UIState
}