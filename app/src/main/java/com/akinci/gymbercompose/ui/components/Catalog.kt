package com.akinci.gymbercompose.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.akinci.gymbercompose.common.network.NetworkState
import com.akinci.gymbercompose.ui.theme.GymberComposeTheme

/**
 *
 *  This file contains components which are used app wide.
 *  Can be considered as component catalog of this app.
 *
 * **/

@Preview(showBackground = true)
@Composable
fun Catalog_PageNavigator() {
    GymberComposeTheme {
        PageNavigator(onClick = { })
    }
}

@Preview(showBackground = true)
@Composable
fun Catalog_OfflineDialog() {
    GymberComposeTheme {
        OfflineDialog(
            networkState = NetworkState.NotConnected, // NetworkState.Connected, NetworkState.None
            onRetry = { }
        )
    }
}