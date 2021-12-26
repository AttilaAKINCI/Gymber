package com.akinci.gymber.data.output

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Partner(
    val id: Int,
    val name: String,
    val slug: String,
    val waitlist_enabled: Boolean,
    val check_in_radius: Int,
    val first_live_at: String,
    val review_count: Int,
    val review_rating: Double,
    val header_image: Map<String, String>,
    val surplus: Surplus,
    val locations: List<Location>,
    val category: Category,
    val settlement_options: SettlementOption
)
