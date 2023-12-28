package com.akinci.gymber.ui.ds.components.swipecards

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.akinci.gymber.ui.ds.components.ActionButton
import com.akinci.gymber.ui.ds.components.Shimmer
import com.akinci.gymber.ui.ds.components.swipecards.data.ActionButtons
import com.akinci.gymber.ui.ds.components.swipecards.data.Direction
import com.akinci.gymber.ui.ds.components.swipecards.data.Image
import com.akinci.gymber.ui.ds.components.swipecards.data.SwipeAction
import com.akinci.gymber.ui.ds.components.swipecards.data.Type
import com.akinci.gymber.ui.ds.theme.Purple
import com.akinci.gymber.ui.ds.theme.RedDark
import com.akinci.gymber.ui.ds.theme.YellowDark
import kotlinx.collections.immutable.PersistentList

@Composable
fun SwipeBox(
    modifier: Modifier = Modifier,
    images: PersistentList<Image>,
    actions: ActionButtons,
    onSwipe: (Direction, Int) -> Unit,
    onDetailButtonClick: (Int) -> Unit,
) {
    // state of index is saved/remembered in terms of leave/return screen
    var index by rememberSaveable { mutableIntStateOf(0) }
    var isShimmerVisible by remember { mutableStateOf(true) }

    var currentImageAction by remember { mutableStateOf(SwipeAction()) }
    val currentImage = runCatching { images[index] }.getOrNull()

    var nextImage by remember { mutableStateOf<Image?>(null) }

    // state of previous image is saved/remembered in terms of leave/return screen
    var previousImage by rememberSaveable { mutableStateOf<Image?>(null) }
    var previousImageAction by rememberSaveable { mutableStateOf(SwipeAction()) }

    Column(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            if (isShimmerVisible) {
                Shimmer(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(shape = MaterialTheme.shapes.extraLarge),
                )
            }

            // Background Image
            nextImage?.let {
                SwipeContent(
                    imageUrl = it.imageUrl,
                    label = it.label,
                )
            }

            // Current Image - swipe-able
            currentImage?.let {
                SwipeImage(
                    image = it,
                    type = Type.CENTER,
                    swipeAction = currentImageAction,
                    onSwipe = { direction ->
                        // deliver swipe feedback parent ui level
                        onSwipe(direction, images[index].id)

                        // current card is swiped to [direction]. Update cards with new set.
                        index++

                        // sub actions for current image swipe
                        when (direction) {
                            Direction.CENTER -> Unit
                            Direction.LEFT -> {
                                // When any card rejected, save it for reverse action.
                                previousImage = runCatching { images[index - 1] }.getOrNull()
                            }

                            Direction.RIGHT -> {
                                // After right card swipe, clear reverse image & action
                                previousImage = null
                                previousImageAction = SwipeAction()
                            }
                        }
                    },
                    onRestore = {
                        isShimmerVisible = false
                        nextImage = runCatching { images[index + 1] }.getOrNull()
                    }
                )
            }

            // Previous Image - reverse swipe-able
            previousImage?.let {
                SwipeImage(
                    image = it,
                    type = Type.REVERSE,
                    swipeAction = previousImageAction,
                    onSwipe = { _ ->
                        // After reverse card swipe, clear reverse image & action
                        previousImage = null
                        previousImageAction = SwipeAction()

                        // previous card is restored with animation. Update cards with new set.
                        index--
                    },
                    onRestore = {}
                )
            }
        }

        SwipeBox.Actions(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(top = 16.dp, bottom = 64.dp),
            actions = actions,
            isReverseVisible = previousImage != null,
            onDetailButtonClick = {
                currentImage?.let { onDetailButtonClick(it.id) }
            },
            onApproveButtonClick = {
                // approve button click will swipe image content to right automatically
                currentImageAction = SwipeAction(direction = Direction.RIGHT)
            },
            onRejectButtonClick = {
                // approve button click will swipe image content to left automatically
                currentImageAction = SwipeAction(direction = Direction.LEFT)
            },
            onReverseButtonClick = {
                // reverse button click will swipe previous image content to center automatically
                previousImageAction = SwipeAction(direction = Direction.CENTER)
            },
        )
    }
}

typealias SwipeBox = Unit

@Composable
private fun SwipeBox.Actions(
    modifier: Modifier = Modifier,
    actions: ActionButtons,
    isReverseVisible: Boolean,
    onDetailButtonClick: () -> Unit,
    onApproveButtonClick: () -> Unit,
    onRejectButtonClick: () -> Unit,
    onReverseButtonClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        val animatedSize: Int by animateIntAsState(
            targetValue = if (isReverseVisible) 64 else 0,
            animationSpec = tween(400),
            label = "",
        )
        Spacer(modifier = Modifier.weight((animatedSize.toFloat() / 64).coerceIn(0.001f, 1f)))
        ActionButton(
            size = animatedSize.dp,
            containerColor = Color.YellowDark,
            painter = painterResource(id = actions.reverseIcon),
            tintColor = Color.White,
            onClick = onReverseButtonClick
        )

        Spacer(modifier = Modifier.weight(1f))
        ActionButton(
            containerColor = Color.RedDark,
            painter = painterResource(id = actions.rejectIcon),
            tintColor = Color.White,
            onClick = onRejectButtonClick
        )
        Spacer(modifier = Modifier.weight(1f))
        ActionButton(
            containerColor = Color.Purple,
            painter = painterResource(id = actions.detailIcon),
            tintColor = Color.White,
            onClick = onDetailButtonClick
        )
        Spacer(modifier = Modifier.weight(1f))
        ActionButton(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            painter = painterResource(id = actions.approveIcon),
            tintColor = Color.White,
            onClick = onApproveButtonClick
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
