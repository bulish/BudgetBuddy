package com.example.budgetbuddy.ui.elements.shared.form

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.db.TransactionType
import com.example.budgetbuddy.ui.theme.BasicMargin

data class RadioOption(
    val value: String,
    val label: String
)

@Composable
fun CustomRadioButton(
    label: String,
    value: String,
    onChange: (String) -> Unit,
) {

    val radioOptions = listOf(
        RadioOption(TransactionType.EXPENSE.value, stringResource(id = R.string.expense)),
        RadioOption(TransactionType.INCOME.value, stringResource(id = R.string.income))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(start = BasicMargin()),
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary
        )

        radioOptions.forEach { text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (text.value == value),
                        onClick = {
                            onChange(text.value)
                        }
                    ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text.value == value),
                    onClick = {
                        onChange(text.value)
                    },
                    enabled = true,
                    colors = RadioButtonDefaults.colors(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    ),
                    interactionSource = remember { MutableInteractionSource() }
                )
                Text(
                    text = text.label,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
