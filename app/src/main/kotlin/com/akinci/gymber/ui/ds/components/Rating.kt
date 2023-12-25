package com.akinci.gymber.ui.ds.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.akinci.gymber.R
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.ds.theme.YellowDark

@Composable
fun Rating(
    modifier: Modifier = Modifier,
    rating: Double,
) {
    Row(modifier = modifier) {
        for (i in (0 until 5)) {
            val value = rating - i
            when {
                value >= 1 -> R.drawable.ic_star_filled_24dp
                value > 0 && value < 1 -> R.drawable.ic_star_half_24dp
                value <= 0 -> R.drawable.ic_star_empty_24dp
                else -> null
            }?.let {
                Icon(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    tint = Color.YellowDark
                )
            }
        }
    }
}

@UIModePreviews
@Composable
private fun RatingPreview() {
    GymberTheme {
        Column {
            Row {
                Text(text = "5.0 -")
                Rating(rating = 5.0)
            }
            Row {
                Text(text = "4.8 -")
                Rating(rating = 4.8)
            }
            Row {
                Text(text = "4.0 -")
                Rating(rating = 4.0)
            }
            Row {
                Text(text = "3.5 -")
                Rating(rating = 3.5)
            }
            Row {
                Text(text = "3.0 -")
                Rating(rating = 3.0)
            }
            Row {
                Text(text = "2.7 -")
                Rating(rating = 2.7)
            }
            Row {
                Text(text = "2.0 -")
                Rating(rating = 2.0)
            }
            Row {
                Text(text = "1.3 -")
                Rating(rating = 1.3)
            }
            Row {
                Text(text = "1.0 -")
                Rating(rating = 1.0)
            }
            Row {
                Text(text = "0.5 -")
                Rating(rating = 0.5)
            }
            Row {
                Text(text = "0.0 -")
                Rating(rating = 0.0)
            }
        }
    }
}