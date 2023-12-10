package com.akinci.gymber.ui.features.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.features.detail.DetailScreenViewContract.State
import com.akinci.gymber.ui.navigation.animation.FadeInOutAnimation
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph
@Destination(style = FadeInOutAnimation::class)
@Composable
fun DetailScreen(
    navigator: DestinationsNavigator,
    vm: DetailScreenViewModel = hiltViewModel(),
) {
    val uiState: State by vm.stateFlow.collectAsStateWithLifecycle()

    DetailScreenContent(
        uiState = uiState,
        onBackPressed = { navigator.popBackStack() }
    )
}

@Composable
private fun DetailScreenContent(
    uiState: State,
    onBackPressed: () -> Unit
) {

    /* val scaffoldState = rememberScaffoldState()
     val scope = rememberCoroutineScope()

     Scaffold(
         scaffoldState = scaffoldState,
     ) {
         Box(
             modifier = Modifier
                 .fillMaxSize()
         ) {

             */
    /** Detail content **//*
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                */
    /** Gym image container **//*
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.3f)
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = vm.partnerState?.header_image?.get("desktop") ?: "",
                            builder = {
                                crossfade(true)
                            }
                        ),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colorResource(R.color.white_60))
                            .align(Alignment.BottomCenter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = vm.partnerState?.name ?: "",
                            color = colorResource(R.color.black_60),
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 0.dp, 8.dp),
                            style = MaterialTheme.typography.h2,
                            fontSize = 24.sp,
                            letterSpacing = TextUnit(0.1f, TextUnitType.Sp)
                        )
                    }
                }

                */
    /** Rating & Map button **//*
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    // Rating
                    RatingBar(
                        modifier = Modifier
                            .align(Alignment.Center),
                        value = vm.partnerState?.review_rating?.toFloat() ?: 0f,
                        activeColor = colorResource(R.color.yellow),
                        inactiveColor = colorResource(R.color.black_85),
                        ratingBarStyle = RatingBarStyle.HighLighted,
                        onValueChange = { },
                        onRatingChanged = { }
                    )

                    // Map Button
                    val mapButtonMessage = stringResource(R.string.open_map_message)
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = mapButtonMessage,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        },
                        modifier = Modifier
                            .scale(0.7f)
                            .padding(0.dp, 0.dp, 20.dp, 0.dp)
                            .align(Alignment.CenterEnd),
                        backgroundColor = colorResource(R.color.teal_200)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_pin),
                            contentDescription = stringResource(R.string.floating_button_content_desc),
                            modifier = Modifier
                                .scale(1.2f),
                            // .padding(5.dp, 0.dp, 0.dp, 0.dp),
                            tint = colorResource(R.color.white_90)
                        )
                    }
                }

                val locationList = remember { mutableStateListOf<Address>() }
                locationList.clear()
                locationList.addAll(vm.partnerState?.locations ?: listOf())

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 10.dp, 0.dp, 30.dp)
                ) {
                    item {
                        */
    /** Description **//*
                        var detailText = stringResource(
                            R.string.gym_detail_info,
                            vm.partnerState?.name ?: "",
                            vm.partnerState?.category?.name ?: "",
                            DateTimeProvider.findOpeningTime(vm.partnerState?.first_live_at ?: "")
                        )
                        if (vm.partnerState?.surplus?.surplus_allowed == true) {
                            detailText += stringResource(
                                R.string.gym_detail_surplus,
                                vm.partnerState?.name ?: "",
                                vm.partnerState?.surplus?.formatted_price ?: ""
                            )
                        }
                        Text(
                            text = detailText,
                            modifier = Modifier
                                .padding(20.dp, 0.dp, 20.dp, 30.dp),
                            style = MaterialTheme.typography.body1
                        )

                        */
    /** Bubble Specs **//*
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(0.dp, 0.dp, 0.dp, 30.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            */
    /** Drop in **//*
                            BubbleSpec(
                                isEnabled = vm.partnerState?.settlement_options?.drop_in_enabled
                                    ?: false,
                                text = stringResource(R.string.drop_in)
                            )
                            */
    /** Reservable **//*
                            BubbleSpec(
                                isEnabled = vm.partnerState?.settlement_options?.reservable_workouts
                                    ?: false,
                                text = stringResource(R.string.reservable)
                            )
                            */
    /** In order **//*
                            BubbleSpec(
                                isEnabled = vm.partnerState?.settlement_options?.first_come_first_serve
                                    ?: false,
                                text = stringResource(R.string.first_come_first_serve)
                            )
                        }

                        */
    /** Location List **//*
                        Text(
                            text = stringResource(R.string.gym_address),
                            modifier = Modifier
                                .padding(20.dp, 0.dp, 0.dp, 10.dp),
                            style = MaterialTheme.typography.body1
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(20.dp, 0.dp, 0.dp, 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = stringResource(R.string.gym_location),
                                style = MaterialTheme.typography.body1
                            )
                            Text(
                                text = stringResource(R.string.gym_distance),
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                    items(locationList) { location ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 0.dp, 20.dp, 0.dp)
                        ) {
                            Text(
                                text = "${location.street_name}, ${location.city}",
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.body1
                            )
                            Text(
                                text = if (location.distance.isNotBlank()) {
                                    stringResource(R.string.distance, location.distance)
                                } else {
                                    stringResource(R.string.distance_unknown)
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }

            } // end of Content

            */
    /** Back Button **//*
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(
                    initialAlpha = 0f
                ),
                exit = fadeOut(
                    targetAlpha = 0f
                )
            ) {
                FloatingActionButton(
                    onClick = { scope.launch { onBackPressed.invoke() } },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp, 10.dp, 0.dp, 0.dp)
                        .scale(0.8f),
                    backgroundColor = colorResource(R.color.white_75)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = stringResource(R.string.floating_button_content_desc),
                        modifier = Modifier
                            .scale(1.2f)
                            .padding(5.dp, 0.dp, 0.dp, 0.dp),
                        tint = colorResource(R.color.gray_75)
                    )
                }
            }

        } // end of Box
    }*/
}

@UIModePreviews
@Composable
private fun DetailScreenPreview() {
    GymberTheme {
        DetailScreenContent(
            uiState = State(),
            onBackPressed = {},
        )
    }
}