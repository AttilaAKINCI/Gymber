package com.akinci.gymber.ui.ds.components.swipecards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.akinci.gymber.R
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.ui.ds.components.CachedImage
import com.akinci.gymber.ui.ds.components.swipecards.data.AnimationType
import com.akinci.gymber.ui.ds.components.swipecards.data.ForcedAction
import com.akinci.gymber.ui.ds.components.swipecards.data.SwipeDirection
import com.akinci.gymber.ui.ds.components.swipecards.data.SwipeImage
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.ds.theme.RedDark
import com.akinci.gymber.ui.ds.theme.halfTransparentSurface
import com.akinci.gymber.ui.ds.theme.titleLarge_bangers
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun SwipeCard(
    modifier: Modifier = Modifier,
    forcedAction: ForcedAction = ForcedAction(),
    image: SwipeImage,
    onSwipe: (SwipeDirection, Int) -> Unit,
    onLoad: () -> Unit,
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val threshold = with(density) { 100.dp.toPx() }
    val swipeValue = with(density) { (configuration.screenWidthDp * 1.1).dp.toPx() }

    var animationType by remember { mutableStateOf(AnimationType.DEFAULT) }
    var isVisible by remember { mutableStateOf(false) }

    var offsetX by remember { mutableFloatStateOf(0f) }
    val animatedOffset: Float by animateFloatAsState(
        targetValue = offsetX,
        animationSpec = tween(durationMillis = animationType.duration.toInt()),
        label = ""
    )

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

    var swipeCount by remember { mutableIntStateOf(0) }
    LaunchedEffect(swipeCount) {
        if (swipeCount > 0) {
            // wait for component to complete swipe animation
            delay(animationType.duration)
            // detect which direction is component swiped
            val swipeDirection = when {
                offsetX > 0 -> SwipeDirection.RIGHT
                offsetX < 0 -> SwipeDirection.LEFT
                else -> null
            }
            // reset component states
            animationType = AnimationType.INSTANT
            isVisible = false
            offsetX = 0f
            // wait for component to restore initial state
            delay(animationType.duration)
            // send swipe feed back
            swipeDirection?.let { onSwipe(it, image.id) }
        }
    }

    LaunchedEffect(forcedAction.swipeRight) {
        if (forcedAction.swipeRight > 0) {
            animationType = AnimationType.DEFAULT
            swipeCount++
            offsetX = swipeValue
        }
    }

    LaunchedEffect(forcedAction.swipeLeft) {
        if (forcedAction.swipeLeft > 0) {
            animationType = AnimationType.DEFAULT
            swipeCount++
            offsetX = -swipeValue
        }
    }

    Box(
        modifier = modifier
            .alpha(if (isVisible) 1f else 0f)
            .offset { IntOffset(animatedOffset.roundToInt(), 0) }
            .rotate(animatedAngle)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { animationType = AnimationType.INSTANT },
                    onDragEnd = {
                        animationType = AnimationType.DEFAULT
                        offsetX = if (offsetX.absoluteValue > threshold) {
                            // threshold is breached, swipe away.
                            swipeCount++
                            if (offsetX > 0) swipeValue else -swipeValue
                        } else {
                            // we couldn't reach threshold, reset position
                            0f
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        if (isVisible) {
                            offsetX += dragAmount.x
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        CachedImage(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(shape = MaterialTheme.shapes.extraLarge),
            imageUrl = image.imageUrl,
            onLoad = {
                isVisible = true
                onLoad()
            }
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(color = Color.halfTransparentSurface),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = image.label,
                style = MaterialTheme.typography.titleLarge_bangers
            )
        }
    }
}


@UIModePreviews
@Composable
fun SwipeCardPreview() {
    GymberTheme {
        SwipeCard(
            image = SwipeImage(id = 100, imageUrl = "", label = ""),
            onSwipe = { _, _ -> },
            onLoad = {}
        )
    }
}
