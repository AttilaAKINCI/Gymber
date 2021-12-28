package com.akinci.gymber.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PartnerListResponse(
    val data: List<Partner>
)
