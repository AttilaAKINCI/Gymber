package com.akinci.gymber.data.remote

import com.akinci.gymber.domain.Partner
import kotlinx.serialization.Serializable

@Serializable
data class PartnerListServiceResponse(
    val data: List<PartnerResponse>
)
