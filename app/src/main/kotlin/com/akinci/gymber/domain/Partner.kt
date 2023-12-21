package com.akinci.gymber.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Partner(
    val id: Int,
    val name: String,
    val description: String,
    val facilities: List<String>,
    val rating: Double,
    val reviewCount: Int,
    val dropInEnabled: Boolean,
    val reservableWorkouts: Boolean,
    val firstComeFirstServe: Boolean,
    val imageUrl: String,
    val locations: List<Location>,
    val distance: String,
) : Parcelable
