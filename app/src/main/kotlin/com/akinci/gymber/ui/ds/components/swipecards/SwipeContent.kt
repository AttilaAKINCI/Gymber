package com.akinci.gymber.ui.ds.components.swipecards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.akinci.gymber.R
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.ui.ds.components.CachedImage
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.ds.theme.RedDark
import com.akinci.gymber.ui.ds.theme.bottomExtraLarge
import com.akinci.gymber.ui.ds.theme.halfTransparentSurface
import com.akinci.gymber.ui.ds.theme.titleLarge_bangers

@Composable
fun SwipeContent(
    modifier: Modifier = Modifier,
    imageUrl: String,
    label: String,
    likeAlpha: Float = 0f,
    dislikeAlpha: Float = 0f,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CachedImage(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(shape = MaterialTheme.shapes.extraLarge),
            imageUrl = imageUrl,
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
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter)
                .clip(MaterialTheme.shapes.bottomExtraLarge)
                .background(color = Color.halfTransparentSurface),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = label,
                style = MaterialTheme.typography.titleLarge_bangers
            )
        }
    }
}

@UIModePreviews
@Composable
private fun CardImagePreview() {
    GymberTheme {
        Surface {
            SwipeContent(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = "https://edge.one.fit/image/partner/image/16280/b7ad750d-8e00-40cb-b590-a6e9c4875d91.jpg?w=1680",
                label = "Rocycle Amsterdam - City",
            )
        }
    }
}

