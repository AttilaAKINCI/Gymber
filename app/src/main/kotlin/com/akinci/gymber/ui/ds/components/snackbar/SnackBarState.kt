package com.akinci.gymber.ui.ds.components.snackbar

import java.util.UUID

data class SnackBarState(
    val id: UUID = UUID.randomUUID(),
    val message: String,
)
