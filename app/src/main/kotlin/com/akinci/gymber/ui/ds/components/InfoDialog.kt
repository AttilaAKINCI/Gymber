package com.akinci.gymber.ui.ds.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.akinci.gymber.core.compose.UIModePreviews
import com.akinci.gymber.ui.ds.theme.GymberTheme
import com.akinci.gymber.ui.ds.theme.bodyLarge_bold

@Composable
fun InfoDialog(
    title: String,
    message: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        confirmButton = {
            TextButton(onClick = onButtonClick) {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.bodyLarge_bold,
                )
            }
        },
    )
}

@UIModePreviews
@Composable
fun ErrorDialogPreview() {
    GymberTheme {
        Surface(Modifier.fillMaxSize()) {
            InfoDialog(
                title = "Info Dialog Title",
                message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                        " In est ante, varius a eleifend sed, ullamcorper nec risus." +
                        " Aliquam tincidunt leo eu erat condimentum ultrices tincidunt " +
                        "vitae purus.",
                buttonText = "Close",
                onButtonClick = {},
                onDismiss = {},
            )
        }
    }
}