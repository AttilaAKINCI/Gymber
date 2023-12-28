package com.akinci.gymber.ui.ds.components.swipecards

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.ui.ds.components.swipecards.data.AnimationType
import com.akinci.gymber.ui.ds.components.swipecards.data.Direction
import com.akinci.gymber.ui.ds.components.swipecards.data.Image
import com.akinci.gymber.ui.ds.components.swipecards.data.SwipeAction
import com.akinci.gymber.ui.ds.components.swipecards.data.Type
import com.akinci.gymber.ui.ds.theme.GymberTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

/**
 * SwipeImage is a component which is capable of moving [SwipeContent] left or right by drag action
 *
 * @property [image] data of [SwipeContent]
 * @property [swipeAction] is action request for automatic swipe right or left
 * @property [onRestore] is an action that triggered when [SwipeImage] is in default position
 * @property [onSwipe] is an action that triggered when [SwipeImage] is swiped out
 *
 * **/
@Composable
fun SwipeImage(
    image: Image,
    type: Type,
    swipeAction: SwipeAction,
    onRestore: () -> Unit,
    onSwipe: (Direction) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val threshold = with(density) { 100.dp.toPx() }
    val swipeValue = with(density) { (configuration.screenWidthDp * 1.1).dp.toPx() }

    val initialOffset = when (type) {
        Type.CENTER -> 0f
        Type.REVERSE -> -swipeValue
    }
    var offset by remember { mutableFloatStateOf(initialOffset) }
    var animation by remember { mutableStateOf(AnimationType.INSTANT) }
    val animatedOffset: Float by animateFloatAsState(
        targetValue = offset,
        animationSpec = when (animation) {
            AnimationType.SOFT -> spring()
            AnimationType.INSTANT, AnimationType.ANIMATED ->
                tween(durationMillis = animation.duration)
        },
        label = "",
    )

    val animatedAngle = (animatedOffset / 10).coerceIn(-10f, 10f)
    val likeAlpha = (animatedOffset / threshold).coerceIn(0f, 1f)
    val dislikeAlpha = (animatedOffset / threshold).coerceIn(-1f, 0f).absoluteValue

    LaunchedEffect(swipeAction.actionId) {
        delay(100)

        swipeAction.direction?.let { direction ->
            val swipeOffset = when (direction) {
                Direction.CENTER -> 0f
                Direction.RIGHT -> swipeValue
                Direction.LEFT -> -swipeValue
            }

            animation = AnimationType.ANIMATED
            offset = swipeOffset

            coroutineScope.launch {
                delay(animation.duration.toLong())
                onSwipe(direction)
            }
        }
    }

    LaunchedEffect(image.imageUrl) {
        // when new image received, we need to restore swipe-able image location instantly.
        animation = AnimationType.INSTANT
        offset = initialOffset

        // animateFloatAsState always takes small bite of time even if we set animationDuration to 0
        //  in order to solve the Image loading glitch, we need to apply small amount of time delay
        coroutineScope.launch {
            delay(100)
            onRestore()
        }
    }

    SwipeContent(
        modifier = Modifier
            .offset { IntOffset(animatedOffset.roundToInt(), 0) }
            .rotate(animatedAngle)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        // on drag start we need to move card instantly.
                        animation = AnimationType.SOFT
                    },
                    onDragEnd = {
                        // on drag end we need to animate card to it's destination.
                        animation = AnimationType.ANIMATED

                        // we couldn't reach threshold, reset position
                        offset = if (offset.absoluteValue < threshold) {
                            0f
                        } else {
                            if (offset > 0) {
                                // positive threshold is breached, swipe right.
                                coroutineScope.launch {
                                    delay(animation.duration.toLong())
                                    onSwipe(Direction.RIGHT)
                                }
                                swipeValue
                            } else {
                                // negative threshold is breached, swipe left.
                                coroutineScope.launch {
                                    delay(animation.duration.toLong())
                                    onSwipe(Direction.LEFT)
                                }
                                -swipeValue
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offset += dragAmount.x
                    }
                )
            },
        imageUrl = image.imageUrl,
        label = image.label,
        likeAlpha = likeAlpha,
        dislikeAlpha = dislikeAlpha,
    )
}

@UIModePreviews
@Composable
fun SwipeImagePreview() {
    GymberTheme {
        SwipeImage(
            image = Image(
                id = 100,
                imageUrl = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=1680",
                label = "Rocycle Amsterdam - City",
            ),
            type = Type.CENTER,
            swipeAction = SwipeAction(),
            onSwipe = { _ -> },
            onRestore = {},
        )
    }
}
