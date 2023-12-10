package com.akinci.gymber.ui.ds.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.akinci.gymber.R
import com.akinci.gymber.ui.ds.theme.oval

@Composable
fun Bubble(
    text: String
) {
    // TODO fix here
    /*TextButton(
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.secondary,
        ),
        onClick = { }
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = text,
            color = if (isEnabled) {
                colorResource(R.color.white)
            } else {
                colorResource(R.color.gray_22)
            }
        )
    }
*/


  /*  Column(
        modifier = Modifier
            .wrapContentWidth()
            .height(36.dp)
            .clip(RoundedCornerShape(18.dp))
            .border(
                border = BorderStroke(1.dp, colorResource(R.color.black)),
                shape = MaterialTheme.shapes.oval
            )
            .background(
                color = if (isEnabled) {
                    colorResource(R.color.teal_200)
                } else {
                    colorResource(R.color.gray_EA)
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

    }*/
}