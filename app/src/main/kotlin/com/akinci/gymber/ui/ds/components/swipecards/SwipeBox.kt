package com.akinci.gymber.ui.ds.components.swipecards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.ui.ds.components.CachedImage
import com.akinci.gymber.ui.ds.components.swipecards.data.ForcedAction
import com.akinci.gymber.ui.ds.components.swipecards.data.SwipeDirection
import com.akinci.gymber.ui.ds.components.swipecards.data.SwipeImage
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.ds.theme.bottomExtraLarge
import com.akinci.gymber.ui.ds.theme.halfTransparentSurface
import com.akinci.gymber.ui.ds.theme.titleLarge_bangers
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SwipeBox(
    modifier: Modifier = Modifier,
    forcedAction: ForcedAction = ForcedAction(),
    images: PersistentList<SwipeImage>,
    onSwipe: (SwipeDirection, Int) -> Unit,
) {
    when {
        images.isEmpty() -> SwipeBox.Loading(modifier = modifier)
        else -> SwipeBox.Content(
            modifier = modifier,
            forcedAction = forcedAction,
            images = images,
            onSwipe = onSwipe
        )
    }
}

typealias SwipeBox = Unit

@Composable
private fun SwipeBox.Content(
    modifier: Modifier = Modifier,
    forcedAction: ForcedAction,
    images: PersistentList<SwipeImage>,
    onSwipe: (SwipeDirection, Int) -> Unit,
) {
    var imageIndex by remember { mutableIntStateOf(0) }

    val currentImage = runCatching { images[imageIndex] }.getOrNull()
    var nextImage by remember { mutableStateOf<SwipeImage?>(null) }
    var previousImage by remember { mutableStateOf<SwipeImage?>(null) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        // put next image as background image
        nextImage?.let {
            Box {
                CachedImage(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(shape = MaterialTheme.shapes.extraLarge),
                    imageUrl = it.imageUrl,
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.BottomCenter)
                        .clip(MaterialTheme.shapes.bottomExtraLarge)
                        .background(color = Color.halfTransparentSurface),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 12.dp),
                        text = it.label,
                        style = MaterialTheme.typography.titleLarge_bangers
                    )
                }
            }
        }

        // put first image upfront as swipeable image
        currentImage?.let {
            SwipeCard(
                image = it,
                forcedAction = forcedAction,
                onSwipe = { direction, id ->
                    // pass action to screen level
                    onSwipe(direction, id)
                    imageIndex++
                },
                onLoad = {
                    nextImage = runCatching { images[imageIndex.inc()] }.getOrNull()
                    previousImage = runCatching { images[imageIndex.dec()] }.getOrNull()
                }
            )
        }
    }
}

@Composable
private fun SwipeBox.Loading(
    modifier: Modifier = Modifier,
) {
    // TODO show shimmer loading here.
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

    }
}

@UIModePreviews
@Composable
private fun SwipeBoxPreview() {
    GymberTheme {
        SwipeBox(
            images = persistentListOf(),
            onSwipe = { _, _ -> }
        )
    }
}
