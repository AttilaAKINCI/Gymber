package com.akinci.gymber.ui.ds.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.akinci.gymber.R
import com.akinci.gymber.domain.Image
import com.akinci.gymber.ui.ds.theme.RedDark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun SwipeableImage(
    modifier: Modifier = Modifier,
    image: Image,
    onDraggedRight: (Int) -> Unit,
    onDraggedLeft: (Int) -> Unit,
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val threshold = with(density) { 100.dp.toPx() }
    val swipeValue = with(density) { (configuration.screenWidthDp * 1.1).dp.toPx() }

    var offsetX by remember { mutableFloatStateOf(0f) }
    val animatedOffset: Float by animateFloatAsState(offsetX, label = "")

    val animatedAngle by remember {
        derivedStateOf { (animatedOffset / 10).coerceIn(-10f, 10f) }
    }
    val likeAlpha by remember {
        derivedStateOf {
            if (animatedOffset <= 0) return@derivedStateOf 0f
            (animatedOffset / threshold).coerceIn(0f, 1f)
        }
    }
    val dislikeAlpha by remember {
        derivedStateOf {
            if (animatedOffset >= 0) return@derivedStateOf 0f
            (-animatedOffset / threshold).coerceIn(0f, 1f)
        }
    }

    var offsetXOnDragEnd by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(offsetXOnDragEnd) {
        delay(500L)
        when {
            offsetXOnDragEnd > 0 -> onDraggedRight(image.id)
            offsetXOnDragEnd < 0 -> onDraggedLeft(image.id)
        }
    }

    Box(
        modifier = modifier
            .offset { IntOffset(animatedOffset.roundToInt(), 0) }
            .rotate(animatedAngle)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        offsetX = if (offsetX.absoluteValue > threshold) {
                            // threshold is breached, swipe away.
                            if (offsetX > 0) swipeValue else -swipeValue
                        } else {
                            // we couldn't reach threshold, reset position
                            0f
                        }
                        offsetXOnDragEnd = offsetX
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                    }
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        val imageRequest = ImageRequest.Builder(context)
            .data(image.imageUrl)
            .dispatcher(Dispatchers.IO)
            .memoryCacheKey(image.imageUrl)
            .diskCacheKey(image.imageUrl)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()

        AsyncImage(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(shape = MaterialTheme.shapes.extraLarge),
            model = imageRequest,
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        Image(
            modifier = Modifier.alpha(likeAlpha),
            painter = painterResource(id = R.drawable.ic_like),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = Color.Green)
        )

        Image(
            modifier = Modifier.alpha(dislikeAlpha),
            painter = painterResource(id = R.drawable.ic_nope),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = Color.RedDark)
        )
    }
}