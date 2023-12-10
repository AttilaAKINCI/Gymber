package com.akinci.gymber.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PartnerResponse(
    val id: Int,
    val name: String,
    val description: String,
    val slug: String,
    val facilities: List<String>,
    @SerialName("review_rating")
    val reviewRating: Double,
    @SerialName("review_count")
    val reviewCount: Int,
    @SerialName("is_favorite")
    val isFavorite: Boolean,
    @SerialName("settlement_options")
    val settlementOptions: SettlementOption,
    @SerialName("distance_from_position")
    val distanceFromPosition: Int,
    val campaign: Boolean,
    @SerialName("is_exclusive")
    val isExclusive: Boolean,
    @SerialName("is_new")
    val isNew: Boolean,
    val category: Category,
    val categories: List<Category>,
    @SerialName("header_image")
    val headerImage: List<Image>,
    @SerialName("location_groups")
    val locationGroups: List<Image>,
    val awards: List<Award>,
)
