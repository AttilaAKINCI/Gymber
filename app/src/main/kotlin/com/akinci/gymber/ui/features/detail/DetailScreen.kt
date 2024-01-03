package com.akinci.gymber.ui.features.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akinci.gymber.R
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.domain.Gym
import com.akinci.gymber.domain.Location
import com.akinci.gymber.ui.ds.components.ActionButton
import com.akinci.gymber.ui.ds.components.Bubble
import com.akinci.gymber.ui.ds.components.CachedImage
import com.akinci.gymber.ui.ds.components.Rating
import com.akinci.gymber.ui.ds.components.snackbar.SnackBarContainer
import com.akinci.gymber.ui.ds.components.systembarcontroller.SystemBarController
import com.akinci.gymber.ui.ds.components.systembarcontroller.rememberSystemBarColors
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.ds.theme.halfTransparentSurface
import com.akinci.gymber.ui.ds.theme.titleLarge_bangers
import com.akinci.gymber.ui.features.detail.DetailViewContract.ScreenArgs
import com.akinci.gymber.ui.features.detail.DetailViewContract.State
import com.akinci.gymber.ui.navigation.animation.FadeInOutAnimation
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph
@Destination(
    style = FadeInOutAnimation::class,
    navArgsDelegate = ScreenArgs::class,
)
@Composable
fun DetailScreen(
    navigator: DestinationsNavigator,
    vm: DetailViewModel = hiltViewModel(),
) {
    val uiState: State by vm.stateFlow.collectAsStateWithLifecycle()

    DetailScreenContent(
        uiState = uiState,
        onBackPressed = { navigator.navigateUp() },
        openMaps = { location, gymName ->
            vm.openGoogleMaps(location = location, gymName = gymName)
        }
    )
}

@Composable
private fun DetailScreenContent(
    uiState: State,
    onBackPressed: () -> Unit,
    openMaps: (Location, String) -> Unit,
) {
    val scrollState = rememberScrollState()
    val statusBarColorStateChangeThreshold = remember { mutableIntStateOf(0) }
    val systemBarColorState by rememberSystemBarColors(
        threshold = statusBarColorStateChangeThreshold,
        scrollState = scrollState
    )

    SystemBarController(systemBarColorState = systemBarColorState)

    Surface {
        SnackBarContainer(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.navigationBars)
                .fillMaxSize(),
            snackBarState = uiState.snackBarState,
        ) {
            Column(
                modifier = Modifier.verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Box {
                    DetailScreen.ImageHeader(
                        modifier = Modifier.fillMaxWidth(),
                        imageUrl = uiState.gym.imageUrl,
                        gymName = uiState.gym.name,
                        onPositioned = { statusBarColorStateChangeThreshold.intValue = it }
                    )

                    // back button
                    ActionButton(
                        modifier = Modifier.padding(top = 48.dp, start = 16.dp),
                        size = 48.dp,
                        containerColor = Color.halfTransparentSurface,
                        painter = rememberVectorPainter(image = Icons.Default.ArrowBack),
                        tintColor = MaterialTheme.colorScheme.onSurface,
                        onClick = onBackPressed
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Rating(rating = uiState.gym.rating)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = buildString {
                            append("(${uiState.gym.rating})")
                            if (uiState.gym.reviewCount > 0) {
                                append(" / ${uiState.gym.reviewCount} Reviews")
                            }
                        },
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = buildString {
                        append(uiState.gym.description)
                        if (uiState.gym.reservableWorkouts) {
                            append("\n\n ${stringResource(id = R.string.detail_screen_reservable_workouts_info)}")
                        }
                        if (uiState.gym.dropInEnabled) {
                            append("\n\n ${stringResource(id = R.string.detail_screen_drop_in_info)}")
                        }
                        if (uiState.gym.firstComeFirstServe) {
                            append("\n\n ${stringResource(id = R.string.detail_screen_first_come_first_served_info)}")
                        }
                    },
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyLarge,
                )

                DetailScreen.Chips(
                    title = stringResource(id = R.string.detail_screen_categories_title),
                    chips = uiState.gym.categories
                )

                DetailScreen.Chips(
                    title = stringResource(id = R.string.detail_screen_facilities_title),
                    chips = uiState.gym.facilities
                )

                if (uiState.gym.locations.isNotEmpty()) {
                    DetailScreen.Locations(
                        title = stringResource(id = R.string.detail_screen_locations_title),
                        locations = uiState.gym.locations,
                        onLocationClick = {
                            openMaps(it, uiState.gym.name)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

typealias DetailScreen = Unit

@Composable
private fun DetailScreen.ImageHeader(
    modifier: Modifier = Modifier,
    imageUrl: String,
    gymName: String,
    onPositioned: (Int) -> Unit,
) {
    Box(modifier = modifier) {
        CachedImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.2f)
                .onGloballyPositioned {
                    onPositioned(it.size.height)
                },
            imageUrl = imageUrl
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(color = Color.halfTransparentSurface),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = gymName,
                style = MaterialTheme.typography.titleLarge_bangers,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DetailScreen.Chips(
    title: String,
    chips: List<String>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )

        FlowRow(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            chips.forEach {
                Bubble(title = it)
            }
        }
    }
}

@Composable
private fun DetailScreen.Locations(
    title: String,
    locations: List<Location>,
    onLocationClick: (Location) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = title,
            style = MaterialTheme.typography.titleLarge
        )

        locations.forEach { location ->
            DetailScreen.Location(
                address = "${location.street}, ${location.number}",
                distanceText = location.distanceText,
                onClick = { onLocationClick(location) }
            )
        }
    }
}

@Composable
private fun DetailScreen.Location(
    address: String,
    distanceText: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = rememberRipple(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = address,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier.padding(16.dp),
                text = distanceText,
                style = MaterialTheme.typography.bodyLarge,
            )

            Icon(
                modifier = Modifier.padding(end = 16.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
            )
        }

        Divider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@UIModePreviews
@Composable
private fun DetailScreenPreview() {
    GymberTheme {
        DetailScreenContent(
            uiState = State(
                gym = Gym(
                    id = 100,
                    name = "Rocycle Amsterdam",
                    description = "Double Shift is meer dan een gym. Ervaar een complete 360 " +
                            "gym experience in deze oude suikerfabriek. Plof na het sporten neer " +
                            "in de lounge en geniet van een heerlijk kopje espresso. Alle " +
                            "groepslessen worden gegeven door ervaren en enthousiaste trainers." +
                            " Je kunt hier terecht voor vrije training, enorm veel workouts en " +
                            "vooral veel Sweat and Joy!",
                    categories = listOf("Yoga", "Gym", "Workout", "Marathon", "Cycling"),
                    facilities = listOf("Airco", "Douche", "Kleedkamers", "Lockers"),
                    rating = 4.73,
                    reviewCount = 5881,
                    dropInEnabled = false,
                    reservableWorkouts = true,
                    firstComeFirstServe = false,
                    imageUrl = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=1680",
                    locations = listOf(
                        Location(
                            latitude = 52.366552,
                            longitude = 4.878497,
                            city = "Amsterdam",
                            postCode = "1016 XP",
                            street = "Nieuwe Passeerdersstraat",
                            number = "12",
                            distance = 100f,
                            distanceText = "100 KM",
                        )
                    ),
                ),
            ),
            onBackPressed = {},
            openMaps = { _, _ -> },
        )
    }
}
