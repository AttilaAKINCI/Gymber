package com.akinci.gymber.ui.ds.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.ds.theme.oval

@Composable
fun Bubble(
    modifier: Modifier = Modifier,
    title: String
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.oval)
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                shape = MaterialTheme.shapes.oval
            )
            .background(color = MaterialTheme.colorScheme.tertiaryContainer),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.surface
        )
    }
}

@UIModePreviews
@Composable
private fun BubblePreview() {
    GymberTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Bubble(title = "Yoga")
            Bubble(title = "Cycling")
            Bubble(title = "Marathon")
            Bubble(title = "Boxing")
        }
    }
}
