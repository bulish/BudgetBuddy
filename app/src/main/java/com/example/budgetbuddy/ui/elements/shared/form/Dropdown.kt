package com.example.budgetbuddy.ui.elements.shared.form

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.R
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.Grey

@Composable
fun <T> Dropdown(
    value: T?,
    noValueMessage: String,
    data: List<T>,
    toStringRepresentation: @Composable (T) -> String,
    onChange: (T) -> Unit
) {
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, Grey, RoundedCornerShape(4.dp))
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isDropdownExpanded = true
                }
                .padding(BasicMargin())
        ) {
            Text(
                text = value?.let { toStringRepresentation?.let { it1 -> it1(it) } } ?: noValueMessage,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        
        DropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = { isDropdownExpanded = false },
            modifier = Modifier
                .padding(horizontal = BasicMargin())
                .align(Alignment.TopStart)
                .width(200.dp)
        ) {
            if (data.isEmpty()) {
                DropdownMenuItem(
                    onClick = {},
                    text = {
                        Text(text = stringResource(id = R.string.no_places_available))
                    }
                )
            } else {
                data.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            onChange(item)
                            isDropdownExpanded = false
                        },
                        text = {
                            Text(
                                text = toStringRepresentation(item)
                            )
                        }
                    )
                }
            }
        }
    }
}
