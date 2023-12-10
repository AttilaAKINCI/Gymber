package com.akinci.gymber.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SettlementOption(
    @SerialName("drop_in_enabled")
    val dropInEnabled: Boolean,
    @SerialName("reservable_workouts")
    val reservableWorkouts: Boolean,
    @SerialName("first_come_first_serve")
    val firstComeFirstServe: Boolean
)
