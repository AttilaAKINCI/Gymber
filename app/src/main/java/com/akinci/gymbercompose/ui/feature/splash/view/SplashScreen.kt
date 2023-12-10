package com.akinci.gymbercompose.ui.feature.splash.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.akinci.gymbercompose.R
import com.akinci.gymbercompose.ui.theme.GymberComposeTheme
import kotlinx.coroutines.delay


private const val SplashWaitTime: Long = 1000 //5000

/**
 * Stateful version of the Podcast player
 */
@Composable
fun SplashScreen(
    onTimeout: ()->Unit
){
    SplashScreenBody(onTimeout = onTimeout)
}

/**
 * Stateless version of the Player screen
 */
@Composable
private fun SplashScreenBody(
    onTimeout: ()->Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier) {
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
                /** Lottie animation **/
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
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GymberComposeTheme {
        SplashScreen(onTimeout = { })
    }
}