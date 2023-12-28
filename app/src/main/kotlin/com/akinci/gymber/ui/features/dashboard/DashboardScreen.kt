package com.akinci.gymber.ui.features.dashboard

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akinci.gymber.R
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.core.permission.PermissionManager
import com.akinci.gymber.ui.ds.components.InfiniteLottieAnimation
import com.akinci.gymber.ui.ds.components.InfoDialog
import com.akinci.gymber.ui.ds.components.TiledBackground
import com.akinci.gymber.ui.ds.components.swipecards.data.ActionButtons
import com.akinci.gymber.ui.ds.components.swipecards.data.Direction
import com.akinci.gymber.ui.ds.components.swipecards.SwipeBox
import com.akinci.gymber.ui.ds.theme.GymberTheme
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
        onDetailButtonClick = { gymId ->
            uiState.gyms.firstOrNull { it.id == gymId }?.let { gym ->
                navigator.navigate(DetailScreenDestination(gym = gym))
            }
        },
        onGymLike = { vm.onGymLike() },
        onGymDislike = { vm.onGymDislike() }
    )
}

@Composable
private fun DashboardScreenContent(
    uiState: State,
    onDetailButtonClick: (Int) -> Unit,
    onGymLike: () -> Unit,
    onGymDislike: () -> Unit,
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

                if (!uiState.isError) {
                    SwipeBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        images = uiState.imageStates,
                        actions = ActionButtons(
                            detailIcon = R.drawable.ic_bag_24dp,
                            approveIcon = R.drawable.ic_check_28dp,
                            rejectIcon = R.drawable.ic_cancel_24dp,
                            reverseIcon = R.drawable.ic_reverse_24dp,
                        ),
                        onSwipe = { direction ->
                            when (direction) {
                                Direction.RIGHT -> onGymLike()
                                Direction.LEFT -> onGymDislike()
                            }
                        },
                        onDetailButtonClick = onDetailButtonClick,
                    )
                } else {
                    // todo show error view.
                }
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
