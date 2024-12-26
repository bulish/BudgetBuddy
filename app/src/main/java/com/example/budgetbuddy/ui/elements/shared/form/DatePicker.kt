package com.example.budgetbuddy.ui.elements.shared.form

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.budgetbuddy.R
import com.example.budgetbuddy.extensions.formatToDisplayString
import com.example.budgetbuddy.extensions.toEpochMillis
import com.example.budgetbuddy.extensions.toLocalDateTime
import com.example.budgetbuddy.ui.theme.Black
import com.example.budgetbuddy.ui.theme.White
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    value: LocalDateTime,
    onChange: (Long) -> Unit,
    testTag: String
) {
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = value.toEpochMillis()
    )

    var selectedDate by remember { mutableStateOf(
        datePickerState.selectedDateMillis?.let {
            it.toLocalDateTime().formatToDisplayString()
        } ?: value.formatToDisplayString()
    )}

    LaunchedEffect(value) {
        selectedDate = datePickerState.selectedDateMillis?.let {
            value.formatToDisplayString()
        }.toString()
    }

    Box(
        modifier = Modifier.fillMaxWidth()
            .testTag(testTag)
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { },
            label = {
                Text(
                    stringResource(id = R.string.date_label),
                    color = MaterialTheme.colorScheme.secondary
                )
                    },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = stringResource(id = R.string.select_date)
                    )
                }
            },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.secondary),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()

                            .padding()

                    ) {

                        MaterialTheme(
                            colorScheme = MaterialTheme.colorScheme
                        ) {
                            Surface(
                                color = White,
                                contentColor = Black
                            ) {
                                DatePicker(
                                    state = datePickerState,
                                    showModeToggle = false,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        TextButton(
                            onClick = {
                                val newDateMillis = datePickerState.selectedDateMillis
                                if (newDateMillis != null) {
                                    onChange(newDateMillis)
                                }
                                showDatePicker = false
                            },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .clip(RoundedCornerShape(8.dp))
                                .padding(horizontal = 2.dp)
                                .padding(vertical = 2.dp)

                        ) {
                            Text(
                                stringResource(id = R.string.confirm),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
