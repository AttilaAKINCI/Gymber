package com.akinci.gymber.ui.features.dashboard

import com.akinci.gymber.core.compose.UIState
import com.akinci.gymber.domain.Partner
import com.akinci.gymber.ui.ds.components.swipecards.data.SwipeImage
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

object DashboardScreenViewContract {

    data class State(
        val partners: PersistentList<Partner> = persistentListOf(),
        val images: PersistentList<SwipeImage> = persistentListOf(),
    ) : UIState
}
