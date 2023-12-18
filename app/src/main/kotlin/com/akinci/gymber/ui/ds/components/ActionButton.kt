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
import androidx.compose.ui.unit.dp
import com.akinci.gymber.R
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.ds.theme.Purple
import com.akinci.gymber.ui.ds.theme.RedDark
import com.akinci.gymber.ui.ds.theme.Teal
import com.akinci.gymber.ui.ds.theme.oval

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    containerColor: Color,
    painter: Painter,
    tintColor: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(64.dp)
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
                containerColor = Color.RedDark,
                painter = painterResource(id = R.drawable.ic_cancel),
                tintColor = Color.White,
                onClick = {}
            )

            ActionButton(
                containerColor = Color.Purple,
                painter = painterResource(id = R.drawable.ic_bag),
                tintColor = Color.White,
                onClick = {}
            )

            ActionButton(
                containerColor = Color.Teal,
                painter = painterResource(id = R.drawable.ic_check),
                tintColor = Color.White,
                onClick = {}
            )
        }
    }
}

