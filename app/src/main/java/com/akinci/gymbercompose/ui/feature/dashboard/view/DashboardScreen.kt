package com.akinci.gymbercompose.ui.feature.dashboard.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.akinci.gymbercompose.R
import com.akinci.gymbercompose.common.helper.state.PermissionState
import com.akinci.gymbercompose.ui.components.NetworkDependentScreen
import com.akinci.gymbercompose.ui.components.TiledBackground
import com.akinci.gymbercompose.ui.feature.dashboard.viewmodel.DashboardViewModel
import com.akinci.gymbercompose.ui.theme.GymberComposeTheme
import kotlinx.coroutines.launch

/**
 * Stateful version of the Podcast player
 */
@ExperimentalAnimationApi
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToDetail: () -> Unit,
    animationCount:Int = Int.MAX_VALUE    // Added for compose ui tests.. Lottie is blocking UI for infinite animation.
){
    DashboardScreenBody(
        vm = viewModel,
        onNavigateToDetail = onNavigateToDetail,
        animationCount = animationCount
    )
}

/**
 * Stateless version of the Player screen
 */
@ExperimentalAnimationApi
@Composable
fun DashboardScreenBody(
    vm: DashboardViewModel,
    onNavigateToDetail: () -> Unit,
    animationCount: Int
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
    ) {
        /** For a trial Dashboard Screen is marked as "Network Dependent Screen" (NDS) **/
        NetworkDependentScreen(retryAction = {  }) {
            TiledBackground(
                tiledDrawableId = R.drawable.pattern
            ){
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ){

                        /** Welcome section **/
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

                            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.gymber))
                            LottieAnimation(
                                composition,
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp),
                                iterations = animationCount
                            )

                            Text(
                                text = stringResource(R.string.dashboard_welcome_info_text),
                                modifier = Modifier.padding(0.dp, 0.dp, 5.dp,0.dp),
                                style = MaterialTheme.typography.body1
                            )
                        }

                        // swipe gym image
                        vm.getTopElement()?.let {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(fraction = 0.8f)
                                .padding(20.dp, 20.dp, 20.dp, 20.dp)
                                .clip(RoundedCornerShape(18.dp))
                            ){
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
                                        text = if(closestLoc.isNotBlank()) {
                                            stringResource(R.string.gym_header_title, it.name, closestLoc)
                                        }else{
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
                            ) { /** NOP **/ }
                        }
                    }

                    /** Bottom Button Section **/
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(0.dp, 0.dp, 0.dp, 50.dp)
                            .align(alignment = Alignment.BottomCenter),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        /** disLike Button **/
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

                        /** select Button **/
                        FloatingActionButton(
                            onClick = { scope.launch {
                                vm.select()
                                onNavigateToDetail.invoke()
                            } },
                            backgroundColor = colorResource(R.color.purple_500)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_bag),
                                contentDescription = stringResource(R.string.floating_button_content_desc),
                                modifier = Modifier.scale(1f),
                                tint = colorResource(R.color.white)
                            )
                        }

                        /** like Button **/
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

                    /** in case of any match make it visible **/
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
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    GymberComposeTheme {
        DashboardScreen(onNavigateToDetail = { })
    }
}