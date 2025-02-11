package com.example.budgetbuddy.ui.elements.shared.labeledelement

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.budgetbuddy.R
import com.example.budgetbuddy.ui.elements.shared.CustomDivider
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.Grey
import com.example.budgetbuddy.ui.theme.QuarterMargin
import com.example.budgetbuddy.ui.theme.TextMargin

@Composable
fun LabeledElement(item: LabeledElementData) {
    Column(Modifier.padding(horizontal = BasicMargin(), vertical = QuarterMargin())) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = stringResource(id = item.label),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

                Text(
                    text = item.data ?: stringResource(id = item.dataInt ?: R.string.something_went_wrong),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Grey,
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier
                        .padding(vertical = TextMargin())
                        .testTag(item.testTag)
                )
            }
        }

        CustomDivider()
    }
}
