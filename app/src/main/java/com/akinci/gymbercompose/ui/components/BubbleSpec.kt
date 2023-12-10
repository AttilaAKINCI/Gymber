package com.akinci.gymbercompose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.akinci.gymbercompose.R

@Composable
fun BubbleSpec(
    isEnabled: Boolean,
    text: String
) {
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .height(36.dp)
            .clip(RoundedCornerShape(18.dp))
            .border(
                BorderStroke(1.dp, colorResource(R.color.black)),
                RoundedCornerShape(18.dp)
            )
            .background(color = if(isEnabled){
                colorResource(R.color.teal_200)
            }else{
                colorResource(R.color.gray_EA)
            }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(10.dp,0.dp,10.dp,0.dp),
            color = if(isEnabled){
                colorResource(R.color.white)
            }else{
                colorResource(R.color.gray_22)
            }
        )
    }
}