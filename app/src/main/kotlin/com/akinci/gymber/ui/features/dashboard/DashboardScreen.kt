package com.akinci.gymber.ui.features.dashboard

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akinci.gymber.R
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.core.extensions.getAppSettingsIntent
import com.akinci.gymber.core.mvi.CollectEffect
import com.akinci.gymber.core.permission.PermissionManager
import com.akinci.gymber.ui.ds.components.CachedImage
import com.akinci.gymber.ui.ds.components.DisableRipple
import com.akinci.gymber.ui.ds.components.InfiniteLottieAnimation
import com.akinci.gymber.ui.ds.components.InfoDialog
import com.akinci.gymber.ui.ds.components.TiledBackground
import com.akinci.gymber.ui.ds.components.swipecards.SwipeBox
import com.akinci.gymber.ui.ds.components.swipecards.data.ActionButtons
import com.akinci.gymber.ui.ds.components.swipecards.data.Direction
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.ds.theme.WhiteDark
import com.akinci.gymber.ui.ds.theme.titleLarge_bangers
import com.akinci.gymber.ui.features.dashboard.DashboardViewContract.Action
import com.akinci.gymber.ui.features.dashboard.DashboardViewContract.Effect
import com.akinci.gymber.ui.features.dashboard.DashboardViewContract.State
import com.akinci.gymber.ui.features.destinations.DetailScreenDestination
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalPermissionsApi::class)
@RootNavGraph
@Destination
@Composable
fun DashboardScreen(
    navigator: DestinationsNavigator,
    vm: DashboardViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val uiState: State by vm.state.collectAsStateWithLifecycle()

    // if match screen visible, first navigationBar back action should be "close match screen"
    BackHandler(
        enabled = uiState.isMatchOverlayVisible,
        onBack = { vm.dismissMatch() }
    )

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            PermissionManager.FINE_LOCATION,
            PermissionManager.COARSE_LOCATION,
        ),
        onPermissionsResult = { permissions ->
            when {
                permissions.values.all { it } -> vm.onAction(Action.OnPermissionGranted)
                else -> vm.onAction(Action.ShowRationaleDialog)
            }
        }
    )

    CollectEffect(effect = vm.effect) { effect ->
        when (effect) {
            Effect.RequestPermission -> permissionState.launchMultiplePermissionRequest()
            is Effect.ShowToastMessage -> Toast.makeText(
                context,
                context.getString(effect.messageId),
                Toast.LENGTH_SHORT,
            ).show()

            is Effect.OpenDetailScreen -> navigator.navigate(
                DetailScreenDestination(gym = effect.gym)
            )
        }
    }

    DashboardScreenContent(
        uiState = uiState,
        onAction = vm::onAction,
    )

    if (uiState.isRationaleDialogVisible) {
        InfoDialog(
            title = stringResource(id = R.string.dashboard_screen_location_permission_title),
            message = stringResource(
                id = R.string.dashboard_screen_location_permission_description
            ),
            buttonText = stringResource(id = R.string.dashboard_screen_location_permission_action_title),
            onButtonClick = {
                context.startActivity(context.getAppSettingsIntent())
                vm.onAction(Action.HideRationaleDialog)
            },
            onDismiss = { vm.onAction(Action.HideRationaleDialog) }
        )
    }

    if (uiState.isErrorDialogVisible) {
        InfoDialog(
            title = stringResource(id = R.string.dashboard_screen_error_title),
            message = stringResource(id = R.string.dashboard_screen_error_description),
            buttonText = stringResource(id = R.string.dashboard_screen_error_button_title),
            onButtonClick = { vm.onAction(Action.OnRefreshButtonClick) },
            onDismiss = {},
        )
    }
}

@Composable
private fun DashboardScreenContent(
    uiState: State,
    onAction: (Action) -> Unit,
) {
    Surface {
        TiledBackground(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.navigationBars)
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_pattern_bg)
        ) {
            Column(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .fillMaxWidth()
            ) {
                // Top welcome bar
                DashboardScreen.TopBar()

                SwipeBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    imageList = uiState.imageStates,
                    actions = ActionButtons(
                        detailIcon = R.drawable.ic_bag_24dp,
                        approveIcon = R.drawable.ic_check_28dp,
                        rejectIcon = R.drawable.ic_cancel_24dp,
                        reverseIcon = R.drawable.ic_reverse_24dp,
                    ),
                    onSwipe = { direction, gymId ->
                        when (direction) {
                            Direction.RIGHT -> onAction(Action.OnLikeButtonClick(gymId))
                            Direction.LEFT -> onAction(Action.OnDislikeButtonClick(gymId))
                            else -> Unit
                        }
                    },
                    onDetailButtonClick = { onAction(Action.OnDetailButtonClick(gymId = it)) },
                )
            }

            DashboardScreen.MatchOverlay(
                isVisible = uiState.isMatchOverlayVisible,
                imageUrl = uiState.matchedGym?.imageUrl.orEmpty(),
                name = uiState.matchedGym?.name.orEmpty(),
                onCallButtonClick = { onAction(Action.OnCallMatchButtonClick) },
                onMessageButtonClick = { onAction(Action.OnMessageMatchButtonClick) },
                onCloseButtonClick = { onAction(Action.OnDismissMatchButtonClick) },
            )
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
                .weight(1f)
                .testTag("welcome-text"),
            text = stringResource(id = R.string.dashboard_screen_welcome_text),
            style = MaterialTheme.typography.titleLarge_bangers,
        )
    }
}

@Composable
private fun DashboardScreen.MatchOverlay(
    isVisible: Boolean,
    imageUrl: String,
    name: String,
    onCallButtonClick: () -> Unit,
    onMessageButtonClick: () -> Unit,
    onCloseButtonClick: () -> Unit,
) {
    DisableRipple {
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.85f))
                .clickable { /* Consume click events */ },
            visible = isVisible,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
        ) {
            InfiniteLottieAnimation(
                modifier = Modifier.fillMaxSize(),
                animationId = R.raw.confetti,
                contentScale = ContentScale.FillBounds,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.systemBars),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.dashboard_screen_match_title),
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.WhiteDark,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 32.dp),
                    text = stringResource(
                        id = R.string.dashboard_screen_match_description,
                        name,
                    ),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.WhiteDark,
                )
                Spacer(modifier = Modifier.weight(1f))
                CachedImage(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .aspectRatio(1.3f)
                        .border(
                            border = BorderStroke(1.dp, color = Color.WhiteDark),
                            shape = MaterialTheme.shapes.extraLarge,
                        )
                        .clip(MaterialTheme.shapes.extraLarge),
                    imageUrl = imageUrl,
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = onCallButtonClick
                ) {
                    Text(
                        text = stringResource(id = R.string.dashboard_screen_match_call_button_title),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = onMessageButtonClick
                ) {
                    Text(
                        text = stringResource(id = R.string.dashboard_screen_match_message_button_title),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = onCloseButtonClick
                ) {
                    Text(
                        text = stringResource(id = R.string.dashboard_screen_match_close_button_title),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@UIModePreviews
@Composable
private fun DashboardScreenPreview() {
    GymberTheme {
        DashboardScreenContent(
            uiState = State(),
            onAction = {},
        )
    }
}
