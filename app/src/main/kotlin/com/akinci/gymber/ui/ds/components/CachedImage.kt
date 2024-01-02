package com.akinci.gymber.ui.ds.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers

/**
 *  CachedImage is a variation of [AsyncImage] which automatically cache it's content.
 *
 *  @property [modifier] compose modifier
 *  @property [imageUrl] image url to load
 *  @property [onLoad] action that triggered when image content is load
 *
 * **/
@Composable
fun CachedImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onLoad: () -> Unit = {},
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .dispatcher(Dispatchers.IO)
        .memoryCacheKey(imageUrl)
        .diskCacheKey(imageUrl)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .listener(onSuccess = { _, _ -> onLoad() })
        .build()

    AsyncImage(
        modifier = modifier,
        model = imageRequest,
        contentScale = ContentScale.Crop,
        contentDescription = null,
    )
}
