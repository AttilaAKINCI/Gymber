package com.akinci.gymbercompose.ui.feature.dashboard.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.akinci.gymbercompose.R
import com.akinci.gymbercompose.common.helper.allCaps
import com.akinci.gymbercompose.data.output.Partner
import com.akinci.gymbercompose.ui.theme.GymberComposeTheme

@Composable
fun MatchScreen(
    partner: Partner? = null,
    animationCount: Int = Int.MAX_VALUE,
    onClose: (()->Unit)? = null,
    onSnackBarMessage: ((String)->Unit)? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.black_85))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { /** absorb click events **/ }
    ) {

        /** Lottie animation **/
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confetti))
        LottieAnimation(
            composition,
            iterations = animationCount,
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center),
        ) {

            // match header text
            Text(
                text = stringResource(R.string.match_title),
                 modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp,0.dp,0.dp,10.dp),
                color = colorResource(R.color.white_90),
                textAlign = TextAlign.Center,
                fontSize = 48.sp
            )

            // match description text
            Text(
                text = stringResource(R.string.match_sub_title, partner?.name ?: ""),
                modifier = Modifier.padding(10.dp,0.dp,0.dp,10.dp),
                color = colorResource(R.color.white_60),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            // matched gym image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(30.dp, 20.dp, 30.dp, 20.dp)
                    .clip(RoundedCornerShape(18.dp))
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = partner?.header_image?.get("desktop") ?: "",
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // action buttons
            val callWarningMessage = stringResource(R.string.match_button_text_call_warning)
            Button(
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp)
                    .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .align(alignment = Alignment.CenterHorizontally),
                onClick = { onSnackBarMessage?.invoke(callWarningMessage) }
            ) {
                Text(
                    text = stringResource(R.string.match_button_text_call).allCaps(),
                    style = MaterialTheme.typography.body1
                )
            }

            val messageWarningMessage = stringResource(R.string.match_button_text_message_warning)
            Button(
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp)
                    .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .align(alignment = Alignment.CenterHorizontally),
                onClick = { onSnackBarMessage?.invoke(messageWarningMessage) }
            ) {
                Text(
                    text = stringResource(R.string.match_button_text_message).allCaps(),
                    style = MaterialTheme.typography.body1
                )
            }

            Button(
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp)
                    .padding(0.dp, 10.dp, 0.dp, 10.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .align(alignment = Alignment.CenterHorizontally),
                onClick = { onClose?.invoke() }
            ) {
                Text(
                    text = stringResource(R.string.match_button_text_swipe).allCaps(),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun MatchScreenPreview() {
    GymberComposeTheme {
        MatchScreen()
    }
}