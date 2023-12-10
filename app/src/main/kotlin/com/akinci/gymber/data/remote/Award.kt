package com.akinci.gymber.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Award(
    val name: String,
    val icon: String,
    val date: String,
    val type: String,
)
