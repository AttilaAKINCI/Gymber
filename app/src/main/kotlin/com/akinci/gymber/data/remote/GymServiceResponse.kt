package com.akinci.gymber.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class GymServiceResponse(
    val data: List<GymResponse>
)
