package com.example.budgetbuddy.ui.elements.settings.settingsItem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.budgetbuddy.ui.elements.shared.form.CustomSwitch
import com.example.budgetbuddy.ui.theme.DoubleMargin
import com.example.budgetbuddy.ui.theme.HalfMargin

@Composable
fun SettingsItem(
    title: String,
    content: String? = null,
    switchValue: Boolean? = null,
    onSwitchChange: ((Boolean) -> Unit)? = null,
    testTag: String
) {
    Row(
        modifier = Modifier
            .padding(vertical = HalfMargin())
            .height(DoubleMargin())
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier.weight(1.0f)
        )
        if (switchValue != null && onSwitchChange != null) {
            CustomSwitch(value = switchValue, onChange = onSwitchChange)
        } else if (content != null) {
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
