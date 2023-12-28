package com.akinci.gymber.ui.ds.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.akinci.gymber.R
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.ds.theme.Purple
import com.akinci.gymber.ui.ds.theme.RedDark
import com.akinci.gymber.ui.ds.theme.YellowDark
import com.akinci.gymber.ui.ds.theme.oval

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    painter: Painter,
    containerColor: Color,
    tintColor: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(size)
            .background(
                color = containerColor,
                shape = MaterialTheme.shapes.oval
            )
            .clip(shape = MaterialTheme.shapes.oval)
            .clickable(
                indication = rememberRipple(),
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.size(size * 0.5f),
            painter = painter,
            contentDescription = null,
            tint = tintColor,
        )
    }
}

@UIModePreviews
@Composable
fun ActionButtonPreview() {
    GymberTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ActionButton(
                containerColor = Color.YellowDark,
                painter = painterResource(id = R.drawable.ic_reverse_24dp),
                tintColor = Color.White,
                onClick = {}
            )

            ActionButton(
                containerColor = Color.RedDark,
                painter = painterResource(id = R.drawable.ic_cancel_24dp),
                tintColor = Color.White,
                onClick = {}
            )

            ActionButton(
                containerColor = Color.Purple,
                painter = painterResource(id = R.drawable.ic_bag_24dp),
                tintColor = Color.White,
                onClick = {}
            )

            ActionButton(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                painter = painterResource(id = R.drawable.ic_check_28dp),
                tintColor = Color.White,
                onClick = {}
            )
        }
    }
}

