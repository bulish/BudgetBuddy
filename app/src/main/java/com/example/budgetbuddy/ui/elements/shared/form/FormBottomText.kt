package com.example.budgetbuddy.ui.elements.shared.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import com.example.budgetbuddy.ui.theme.QuarterMargin

@Composable
fun FormBottomText(
    question: String? = null,
    text: String,
    onClickHandler: () -> Unit,
    testTag: String
) {
    Row {
        if (question != null) {
            Text(
                text = question,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Spacer(modifier = Modifier.width(QuarterMargin()))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.clickable() {
                onClickHandler()
            }.testTag(testTag)
        )
    }
}
