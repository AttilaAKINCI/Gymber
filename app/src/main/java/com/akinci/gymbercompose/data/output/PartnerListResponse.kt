package com.akinci.gymbercompose.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PartnerListResponse(
    val data: List<Partner>
)
