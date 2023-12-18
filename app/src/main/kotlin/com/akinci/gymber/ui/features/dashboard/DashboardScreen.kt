package com.akinci.gymber.ui.features.dashboard

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akinci.gymber.R
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.ui.ds.components.TiledBackground
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.features.dashboard.DashboardScreenViewContract.State
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
        openPartnerDetail = {},
    )
}

@Composable
private fun DashboardScreenContent(
    uiState: State,
    openPartnerDetail: () -> Unit,
) {
    Surface {
        TiledBackground(
            painter = painterResource(id = R.drawable.ic_pattern_bg)
        ) {

        }
    }

    /* val scaffoldState = rememberScaffoldState()
     val scope = rememberCoroutineScope()

     Scaffold(
         scaffoldState = scaffoldState,
     ) {
         */
    /** For a trial Dashboard Screen is marked as "Network Dependent Screen" (NDS) **//*
        */
    /** NOP **//*
        NetworkDependentScreen(retryAction = { }) {
            */
    /** NOP **//*
            TiledBackground(
                tiledDrawableId = R.drawable.ic_pattern_bg
            ) {
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        */
    /** Welcome section **//*
                        */
    /** Welcome section **//*
                        */
    /** Welcome section **//*

                        */
    /** Welcome section **//*
                        Row(
                            modifier = Modifier
                                .padding(20.dp, 20.dp, 20.dp, 10.dp)
                                .height(100.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    BorderStroke(1.dp, colorResource(R.color.black)),
                                    RoundedCornerShape(10.dp)
                                )
                                .background(color = colorResource(R.color.white_90)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            val composition by rememberLottieComposition(
                                LottieCompositionSpec.RawRes(
                                    R.raw.gymber
                                )
                            )
                            LottieAnimation(
                                composition,
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp),
                                iterations = animationCount
                            )

                            Text(
                                text = stringResource(R.string.dashboard_welcome_info_text),
                                modifier = Modifier.padding(0.dp, 0.dp, 5.dp, 0.dp),
                                style = MaterialTheme.typography.body1
                            )
                        }

                        // swipe gym image
                        vm.getTopElement()?.let {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(fraction = 0.8f)
                                    .padding(20.dp, 20.dp, 20.dp, 20.dp)
                                    .clip(RoundedCornerShape(18.dp))
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = it.header_image["desktop"],
                                        builder = {
                                            crossfade(true)
                                        }
                                    ),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                )

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(colorResource(R.color.white_60))
                                        .align(Alignment.BottomCenter),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    val closestLoc = vm.findClosestLocation(it)
                                    Text(
                                        text = if (closestLoc.isNotBlank()) {
                                            stringResource(
                                                R.string.gym_header_title,
                                                it.name,
                                                closestLoc
                                            )
                                        } else {
                                            it.name
                                        },
                                        color = colorResource(R.color.black_60),
                                        modifier = Modifier
                                            .padding(10.dp, 0.dp, 10.dp, 10.dp),
                                        style = MaterialTheme.typography.h2
                                    )
                                }
                            }
                        } ?: run {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(fraction = 0.8f)
                                    .padding(20.dp)
                                    .clip(RoundedCornerShape(18.dp))
                                    .alpha(0.8f)
                                    .background(colorResource(R.color.gray_99))
                            ) { */
    /** NOP **//* }
                        }
                    }

                    */
    /** Bottom Button Section **//*

                    */
    /** Bottom Button Section **//*

                    */
    /** Bottom Button Section **//*

                    */
    /** Bottom Button Section **//*
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(0.dp, 0.dp, 0.dp, 50.dp)
                            .align(alignment = Alignment.BottomCenter),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        */
    /** disLike Button **//*
                        */
    /** disLike Button **//*
                        */
    /** disLike Button **//*

                        */
    /** disLike Button **//*
                        FloatingActionButton(
                            onClick = { scope.launch { vm.dislike() } },
                            backgroundColor = colorResource(R.color.red_dark)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_cancel),
                                contentDescription = stringResource(R.string.floating_button_content_desc),
                                modifier = Modifier.scale(1f),
                                tint = colorResource(R.color.white)
                            )
                        }

                        */
    /** select Button **//*

                        */
    /** select Button **//*

                        */
    /** select Button **//*

                        */
    /** select Button **//*
                        FloatingActionButton(
                            onClick = {
                                scope.launch {
                                    vm.select()
                                    onNavigateToDetail.invoke()
                                }
                            },
                            backgroundColor = colorResource(R.color.purple_500)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_bag),
                                contentDescription = stringResource(R.string.floating_button_content_desc),
                                modifier = Modifier.scale(1f),
                                tint = colorResource(R.color.white)
                            )
                        }

                        */
    /** like Button **//*

                        */
    /** like Button **//*

                        */
    /** like Button **//*

                        */
    /** like Button **//*
                        FloatingActionButton(
                            onClick = { scope.launch { vm.like() } }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_check),
                                contentDescription = stringResource(R.string.floating_button_content_desc),
                                modifier = Modifier.scale(1f),
                                tint = colorResource(R.color.white)
                            )
                        }
                    }

                    */
    /** in case of any match make it visible **//*

                    */
    /** in case of any match make it visible **//*

                    */
    /** in case of any match make it visible **//*

                    */
    /** in case of any match make it visible **//*
                    AnimatedVisibility(
                        visible = vm.matchState,
                        enter = fadeIn(
                            initialAlpha = 0f
                        ),
                        exit = fadeOut(
                            targetAlpha = 0f
                        )
                    ) {
                        MatchScreen(
                            partner = vm.partnerState,
                            onClose = { vm.dismissMatchState() },
                            onSnackBarMessage = { message ->
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = message,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        )
                    }

                }
            }
        }
    }*/
}

@UIModePreviews
@Composable
fun DashboardScreenPreview() {
    GymberTheme {
        DashboardScreenContent(
            uiState = State(),
            openPartnerDetail = {},
        )
    }
}
