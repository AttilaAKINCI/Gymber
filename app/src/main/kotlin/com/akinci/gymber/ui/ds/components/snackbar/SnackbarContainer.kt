package com.akinci.gymber.ui.ds.components.snackbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun SnackBarContainer(
    snackBarState: SnackBarState,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(snackBarState.id) {
        if (snackBarState.message.isNotBlank()) {
            coroutineScope.launch {
                snackState.showSnackbar(snackBarState.message)
            }
        }
    }

    Box(modifier = modifier) {
        // Composable content container.
        content()

        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackState,
        )
    }
}