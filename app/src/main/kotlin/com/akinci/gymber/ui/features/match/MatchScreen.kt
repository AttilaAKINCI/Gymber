package com.akinci.gymber.ui.features.match

import androidx.compose.runtime.Composable
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.data.remote.PartnerResponse
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph
@Destination
@Composable
fun MatchScreen(
    partner: PartnerResponse? = null,
    animationCount: Int = Int.MAX_VALUE,
    onClose: (() -> Unit)? = null,
    onSnackBarMessage: ((String) -> Unit)? = null
) {

    MatchScreenContent()


    /* Box(
         modifier = Modifier
             .fillMaxSize()
             .background(colorResource(R.color.black_85))
             .clickable(
                 indication = null,
                 interactionSource = remember { MutableInteractionSource() }
             ) { */
    /** absorb click events **//* }
    ) {

        */
    /** Lottie animation **//*
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
    }*/
}

@Composable
private fun MatchScreenContent(

) {

}

@UIModePreviews
@Composable
private fun MatchScreenPreview() {
    GymberTheme {
        MatchScreenContent()
    }
}