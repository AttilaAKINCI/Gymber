package com.akinci.gymbercompose.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SettlementOption(
    val drop_in_enabled: Boolean,
    val reservable_workouts: Boolean,
    val first_come_first_serve: Boolean
)
