package com.akinci.gymber.data.rest.gym

import kotlinx.serialization.Serializable

@Serializable
data class GymServiceResponse(
    val data: List<GymResponse>
)
