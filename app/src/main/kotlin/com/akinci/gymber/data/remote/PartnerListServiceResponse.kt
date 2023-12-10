package com.akinci.gymber.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class PartnerListServiceResponse(
    val data: List<PartnerResponse>
)
