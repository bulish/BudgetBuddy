package com.example.budgetbuddy.ui.elements.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.budgetbuddy.model.PrimaryColor
import com.example.budgetbuddy.ui.screens.settings.TestTagSettingsScreenColorDropdownItem

@Composable
fun ColorDropdown(
    color: PrimaryColor,
    onChange: (color: PrimaryColor) -> Unit,
    testTag: String
) {
    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    var colors = PrimaryColor.entries

    val itemPosition = remember {
        mutableStateOf(colors.indexOf(color).takeIf { it >= 0 } ?: 0)
    }

    Column(
        modifier = Modifier.testTag(testTag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                Text(
                    text = stringResource(id = colors[itemPosition.value].getStringResource()),
                    color = MaterialTheme.colorScheme.secondary
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                colors.forEachIndexed { index, color ->
                    DropdownMenuItem(text = {
                        Text(
                            text = stringResource(id = color.getStringResource()),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.value = index
                            onChange(color)
                        },
                        modifier = Modifier.testTag("${TestTagSettingsScreenColorDropdownItem}${index}")
                    )
                }
            }
        }

    }
}