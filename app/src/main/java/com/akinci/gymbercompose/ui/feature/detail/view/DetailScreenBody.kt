package com.akinci.gymbercompose.ui.feature.detail.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.akinci.gymbercompose.R
import com.akinci.gymbercompose.common.helper.DateTimeProvider
import com.akinci.gymbercompose.common.helper.LocationProvider
import com.akinci.gymbercompose.data.output.Address
import com.akinci.gymbercompose.ui.components.BubbleSpec
import com.akinci.gymbercompose.ui.feature.dashboard.viewmodel.DashboardViewModel
import com.akinci.gymbercompose.ui.theme.GymberComposeTheme
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import kotlinx.coroutines.launch

/**
 * Stateful version of the Podcast player
 */
@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun DetailScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
){
    DetailScreenBody(
        vm = viewModel,
        onBackPressed = onBackPressed
    )
}

/**
 * Stateless version of the Player screen
 */
@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
fun DetailScreenBody(
    vm: DashboardViewModel,
    onBackPressed: () -> Unit
) {

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            /** Detail content **/
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                /** Gym image container **/
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
                                .padding(0.dp,0.dp,0.dp,8.dp),
                            style = MaterialTheme.typography.h2,
                            fontSize = 24.sp,
                            letterSpacing = TextUnit(0.1f, TextUnitType.Sp)
                        )
                    }
                }

                /** Rating & Map button **/
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ){
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
                        /** Description **/
                        var detailText = stringResource(
                            R.string.gym_detail_info,
                            vm.partnerState?.name ?: "",
                            vm.partnerState?.category?.name ?: "",
                            DateTimeProvider.findOpeningTime(vm.partnerState?.first_live_at ?: "")
                        )
                        if(vm.partnerState?.surplus?.surplus_allowed == true){
                            detailText += stringResource(R.string.gym_detail_surplus, vm.partnerState?.name ?: "", vm.partnerState?.surplus?.formatted_price ?: "")
                        }
                        Text(
                            text = detailText,
                            modifier = Modifier
                                .padding(20.dp, 0.dp, 20.dp, 30.dp),
                            style = MaterialTheme.typography.body1
                        )

                        /** Bubble Specs **/
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(0.dp, 0.dp, 0.dp, 30.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            /** Drop in **/
                            BubbleSpec(
                                isEnabled = vm.partnerState?.settlement_options?.drop_in_enabled ?: false,
                                text = stringResource(R.string.drop_in)
                            )
                            /** Reservable **/
                            BubbleSpec(
                                isEnabled = vm.partnerState?.settlement_options?.reservable_workouts ?: false,
                                text = stringResource(R.string.reservable)
                            )
                            /** In order **/
                            BubbleSpec(
                                isEnabled = vm.partnerState?.settlement_options?.first_come_first_serve ?: false,
                                text = stringResource(R.string.first_come_first_serve)
                            )
                        }

                        /** Location List **/
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
                        ){
                            Text(
                                text = "${location.street_name}, ${location.city}",
                                modifier = Modifier
                                    .align(Alignment.CenterStart),
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.body1
                            )
                            Text(
                                text = if(location.distance.isNotBlank()) {
                                    stringResource(R.string.distance, location.distance)
                                }else{
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

            /** Back Button **/
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
    }
}

@ExperimentalAnimationApi
@ExperimentalUnitApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GymberComposeTheme {
        DetailScreen(onBackPressed = { })
    }
}