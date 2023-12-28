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
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch

/**
 *  SnackBarContainer makes your composable content snackBar message aware without scaffold.
 *
 *  @property [modifier] compose modifier
 *  @property [snackBarState] state of snackBar that holds unique id and message.
 *  @property [content] compose content
 * **/
@Composable
fun SnackBarContainer(
    modifier: Modifier = Modifier,
    snackBarState: SnackBarState?,
    content: @Composable BoxScope.() -> Unit
) {
    val snackState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    snackBarState?.let { state ->
        val errorMessage = stringResource(id = state.messageId)
        LaunchedEffect(state.id) {
            coroutineScope.launch {
                snackState.showSnackbar(errorMessage)
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
