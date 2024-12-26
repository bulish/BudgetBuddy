package com.example.budgetbuddy.ui.elements.shared

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.budgetbuddy.R
import com.example.budgetbuddy.ui.theme.Black
import com.example.budgetbuddy.ui.theme.Green
import com.example.budgetbuddy.ui.theme.White

const val TestTagConfirmButton = "TestTagConfirmButton"

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = stringResource(id = R.string.alert_dialog_icon))
        },
        title = {
            Text(
                text = dialogTitle,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Text(
                text = dialogText,
                style = MaterialTheme.typography.bodySmall.copy (
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.primary
                ),
                textAlign = TextAlign.Center
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Green
                ),
                modifier = Modifier.testTag(TestTagConfirmButton)
            ) {
                Text(
                    stringResource(id = R.string.alert_dialog_confirm),
                    color = White
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Black
                )
            ) {
                Text(
                    stringResource(id = R.string.alert_dialog_dismiss),
                    color = White
                )
            }
        }
    )
}
