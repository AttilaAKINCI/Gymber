package com.akinci.gymber.ui.features.dashboard

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akinci.gymber.R
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.core.permission.PermissionManager
import com.akinci.gymber.ui.ds.components.ActionButton
import com.akinci.gymber.ui.ds.components.InfiniteLottieAnimation
import com.akinci.gymber.ui.ds.components.InfoDialog
import com.akinci.gymber.ui.ds.components.TiledBackground
import com.akinci.gymber.ui.ds.components.swipecards.SwipeBox
import com.akinci.gymber.ui.ds.components.swipecards.data.ForcedAction
import com.akinci.gymber.ui.ds.components.swipecards.data.SwipeDirection
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.ds.theme.Purple
import com.akinci.gymber.ui.ds.theme.RedDark
import com.akinci.gymber.ui.ds.theme.titleLarge_bangers
import com.akinci.gymber.ui.features.dashboard.DashboardViewContract.State
import com.akinci.gymber.ui.features.destinations.DetailScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@RootNavGraph
@Destination
@Composable
fun DashboardScreen(
    navigator: DestinationsNavigator,
    vm: DashboardViewModel = hiltViewModel(),
) {
    val uiState: State by vm.stateFlow.collectAsStateWithLifecycle()

    // Location permission local component
    DashboardScreen.LocationPermission(
        requestPermission = uiState.isPermissionRequired,
        requestRationale = uiState.shouldShowRationale,
        onPermissionResult = { isGranted -> vm.onLocationPermissionResult(isGranted) },
        hideLocationRationaleDialog = { vm.hideRationaleDialog() },
    )

    DashboardScreenContent(
        uiState = uiState,
        onDetailButtonClick = {
            // TODO pass selected gym details here..
            navigator.navigate(
                DetailScreenDestination(
                    gym = uiState.gyms.first()
                )
            )
        },
        onGymLike = {
            // TODO
        },
        onGymDislike = {
            // TODO
        }
    )
}

@Composable
private fun DashboardScreenContent(
    uiState: State,
    onDetailButtonClick: () -> Unit,
    onGymLike: (Int) -> Unit,
    onGymDislike: (Int) -> Unit,
) {
    Surface {
        TiledBackground(
            painter = painterResource(id = R.drawable.ic_pattern_bg)
        ) {
            Column(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .fillMaxWidth()
            ) {
                // Top welcome bar
                DashboardScreen.TopBar()

                // Swipe box
                var forcedActions by remember { mutableStateOf(ForcedAction()) }
                SwipeBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    forcedAction = forcedActions,
                    images = uiState.images,
                    onSwipe = { direction, id ->
                        // TODO send actions to VM, in order to deliver to backend
                        when (direction) {
                            SwipeDirection.RIGHT -> onGymLike(id)
                            SwipeDirection.LEFT -> onGymDislike(id)
                        }
                    }
                )

                DashboardScreen.Actions(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .padding(top = 16.dp, bottom = 64.dp),
                    onDetailButtonClick = onDetailButtonClick,
                    onLikeButtonClick = {
                        // button click will force swipe card to fling away in direction
                        forcedActions = forcedActions.swipeRight()
                    },
                    onDislikeButtonClick = {
                        // button click will force swipe card to fling away in direction
                        forcedActions = forcedActions.swipeLeft()
                    }
                )
            }
        }
    }
}

typealias DashboardScreen = Unit

@Composable
private fun DashboardScreen.TopBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.extraLarge,
            )
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                shape = MaterialTheme.shapes.extraLarge,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        InfiniteLottieAnimation(
            modifier = Modifier
                .width(100.dp)
                .aspectRatio(1f),
            animationId = R.raw.gymber
        )

        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(end = 8.dp)
                .weight(1f),
            text = stringResource(id = R.string.dashboard_screen_welcome_text),
            style = MaterialTheme.typography.titleLarge_bangers,
        )
    }
}

@Composable
private fun DashboardScreen.Actions(
    modifier: Modifier = Modifier,
    onDetailButtonClick: () -> Unit,
    onLikeButtonClick: () -> Unit,
    onDislikeButtonClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        ActionButton(
            containerColor = Color.RedDark,
            painter = painterResource(id = R.drawable.ic_cancel_24dp),
            tintColor = Color.White,
            onClick = onDislikeButtonClick
        )

        ActionButton(
            containerColor = Color.Purple,
            painter = painterResource(id = R.drawable.ic_bag_24dp),
            tintColor = Color.White,
            onClick = onDetailButtonClick
        )

        ActionButton(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            painter = painterResource(id = R.drawable.ic_check_28dp),
            tintColor = Color.White,
            onClick = onLikeButtonClick
        )
    }
}

@Composable
fun DashboardScreen.LocationPermission(
    requestPermission: Boolean,
    requestRationale: Boolean,
    onPermissionResult: (Boolean) -> Unit,
    hideLocationRationaleDialog: () -> Unit,
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionResults ->
        onPermissionResult(permissionResults.values.all { it })
    }

    LaunchedEffect(requestPermission) {
        if (requestPermission) {
            delay(500L)

            permissionLauncher.launch(
                arrayOf(
                    PermissionManager.FINE_LOCATION,
                    PermissionManager.COARSE_LOCATION,
                )
            )
        }
    }

    if (requestRationale) {
        InfoDialog(
            title = stringResource(id = R.string.dashboard_screen_location_permission_title),
            message = stringResource(
                id = R.string.dashboard_screen_location_permission_description
            ),
            buttonText = stringResource(
                id = R.string.dashboard_screen_location_permission_action_title
            ),
            onButtonClick = {
                hideLocationRationaleDialog()
            },
            onDismiss = { hideLocationRationaleDialog() }
        )
    }
}

@UIModePreviews
@Composable
private fun DashboardScreenPreview() {
    GymberTheme {
        DashboardScreenContent(
            uiState = State(isPermissionRequired = true),
            onDetailButtonClick = {},
            onGymLike = {},
            onGymDislike = {},
        )
    }
}
