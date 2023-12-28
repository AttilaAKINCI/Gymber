package com.akinci.gymber.ui.ds.components.swipecards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.akinci.gymber.ui.ds.theme.Purple
import com.akinci.gymber.ui.ds.theme.RedDark
import com.akinci.gymber.ui.ds.theme.YellowDark
import kotlinx.collections.immutable.PersistentList

@Composable
fun SwipeBox(
    modifier: Modifier = Modifier,
    images: PersistentList<Image>,
    actions: ActionButtons,
    onSwipe: (Direction) -> Unit,
    onDetailButtonClick: (Int) -> Unit,
) {
    // state of index is saved/remembered in terms of leave/return screen
    var index by rememberSaveable { mutableIntStateOf(0) }
    var isShimmerVisible by remember { mutableStateOf(true) }

    val currentImage = runCatching { images[index] }.getOrNull()
    var nextImage by remember { mutableStateOf<Image?>(null) }
    var previousImage by remember { mutableStateOf<Image?>(null) }

    var currentImageAction by remember { mutableStateOf(SwipeAction()) }

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
                    image = currentImage,
                    swipeAction = currentImageAction,
                    onSwipe = { direction ->
                        // card moved to [direction] of the screen with animation.
                        onSwipe(direction)
                        index++
                    },
                    onRestore = {
                        isShimmerVisible = false
                        nextImage = runCatching { images[index + 1] }.getOrNull()
                        previousImage = runCatching { images[index - 1] }.getOrNull()
                    }
                )
            }

            // Previous Image - reverse swipe-able
            previousImage?.let {
                // TODO complete.
            }
        }

        SwipeBox.Actions(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(top = 16.dp, bottom = 64.dp),
            actions = actions,
            onDetailButtonClick = {
                currentImage?.let { onDetailButtonClick(it.id) }
            },
            onApproveButtonClick = {
                // approve button click will swipe image content right automatically
                currentImageAction = SwipeAction(direction = Direction.RIGHT)
            },
            onRejectButtonClick = {
                // approve button click will swipe image content right automatically
                currentImageAction = SwipeAction(direction = Direction.LEFT)
            },
            onReplayButtonClick = {},
        )
    }
}

typealias SwipeBox = Unit

@Composable
private fun SwipeBox.Actions(
    modifier: Modifier = Modifier,
    actions: ActionButtons,
    onDetailButtonClick: () -> Unit,
    onApproveButtonClick: () -> Unit,
    onRejectButtonClick: () -> Unit,
    onReplayButtonClick: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly,
    ) {
        ActionButton(
            containerColor = Color.YellowDark,
            painter = painterResource(id = actions.reverseIcon),
            tintColor = Color.White,
            onClick = onReplayButtonClick
        )

        ActionButton(
            containerColor = Color.RedDark,
            painter = painterResource(id = actions.rejectIcon),
            tintColor = Color.White,
            onClick = onRejectButtonClick
        )

        ActionButton(
            containerColor = Color.Purple,
            painter = painterResource(id = actions.detailIcon),
            tintColor = Color.White,
            onClick = onDetailButtonClick
        )

        ActionButton(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            painter = painterResource(id = actions.approveIcon),
            tintColor = Color.White,
            onClick = onApproveButtonClick
        )
    }
}
