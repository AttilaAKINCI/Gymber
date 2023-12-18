package com.akinci.gymber.ui.features.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
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
import com.akinci.gymber.ui.ds.components.ActionButton
import com.akinci.gymber.ui.ds.components.InfiniteLottieAnimation
import com.akinci.gymber.ui.ds.components.TiledBackground
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.ds.theme.Purple
import com.akinci.gymber.ui.ds.theme.RedDark
import com.akinci.gymber.ui.ds.theme.Teal
import com.akinci.gymber.ui.ds.theme.titleLarge_bangers
import com.akinci.gymber.ui.features.dashboard.DashboardScreenViewContract.State
import com.akinci.gymber.ui.features.destinations.DetailScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph
@Destination
@Composable
fun DashboardScreen(
    navigator: DestinationsNavigator,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val uiState: State by viewModel.stateFlow.collectAsStateWithLifecycle()

    DashboardScreenContent(
        uiState = uiState,
        onDetailButtonClick = {
            // TODO pass data ?
            navigator.navigate(DetailScreenDestination)
        },
    )
}

@Composable
private fun DashboardScreenContent(
    uiState: State,
    onDetailButtonClick: () -> Unit,
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

                // Gym Cards
                DashboardScreen.Cards(modifier = Modifier.weight(1f))

                // Action Buttons
                DashboardScreen.Actions(
                    onDetailButtonClick = {},
                    onDislikeButtonClick = {},
                    onLikeButtonClick = {},
                )
            }
        }
    }
}

typealias DashboardScreen = Unit

@Composable
fun DashboardScreen.TopBar(
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
fun DashboardScreen.Cards(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {

    }
}

@Composable
fun DashboardScreen.Actions(
    modifier: Modifier = Modifier,
    onDetailButtonClick: () -> Unit,
    onLikeButtonClick: () -> Unit,
    onDislikeButtonClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(bottom = 64.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        ActionButton(
            containerColor = Color.RedDark,
            painter = painterResource(id = R.drawable.ic_cancel),
            tintColor = Color.White,
            onClick = onDislikeButtonClick
        )

        ActionButton(
            containerColor = Color.Purple,
            painter = painterResource(id = R.drawable.ic_bag),
            tintColor = Color.White,
            onClick = onDetailButtonClick
        )

        ActionButton(
            containerColor = Color.Teal,
            painter = painterResource(id = R.drawable.ic_check),
            tintColor = Color.White,
            onClick = onLikeButtonClick
        )
    }
}

@UIModePreviews
@Composable
fun DashboardScreenPreview() {
    GymberTheme {
        DashboardScreenContent(
            uiState = State(),
            onDetailButtonClick = {},
        )
    }
}
