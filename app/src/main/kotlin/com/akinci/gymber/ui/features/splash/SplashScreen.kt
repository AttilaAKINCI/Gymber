package com.akinci.gymber.ui.features.splash

import androidx.compose.runtime.Composable
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
) {

    SplashScreenContent()
}

@Composable
private fun SplashScreenContent(

) {
    /*Surface(modifier) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val currentOnTimeout by rememberUpdatedState(onTimeout)
            LaunchedEffect(true) {
                delay(SplashWaitTime) // Simulates loading things
                currentOnTimeout()
            }

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(0.dp,0.dp,0.dp,100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                */
    /** Lottie animation **//*
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.gymber))
                LottieAnimation(
                    composition,
                    modifier = Modifier
                        .height(300.dp),
                    iterations = Int.MAX_VALUE
                )

                Text(
                    text = stringResource(R.string.app_name_2),
                    style = MaterialTheme.typography.h2
                )
            }
        }
    }*/
}

@UIModePreviews
@Composable
private fun SplashScreenPreview() {
    GymberTheme {
        SplashScreenContent()
    }
}