package com.example.budgetbuddy.ui.elements.shared

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun CurrencyDropdown(
    currency: String,
    onChange: (currency: String) -> Unit,
    isDark: Boolean? = false,
    currencies: Map<String, Double>?,
    testTag: String
) {
    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    var currencyKeys = currencies?.keys?.toList() ?: emptyList()

    val itemPosition = remember {
        mutableIntStateOf(
            if (currencyKeys.isNotEmpty()) {
                currencyKeys.indexOf(currency).takeIf { it >= 0 } ?: 0
            } else {
                0
            }
        )
    }

    LaunchedEffect(currencies) {
        currencyKeys = currencies?.keys?.toList() ?: emptyList()
        itemPosition.intValue = if (currencyKeys.isNotEmpty()) {
            currencyKeys.indexOf(currency).takeIf { it >= 0 } ?: 0
        } else {
            0
        }
    }

    Column(
        modifier = Modifier
            .then(
                if (isDark == false) Modifier.fillMaxWidth() else Modifier
            )
            .testTag(testTag),
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
                    text = if (currencyKeys.isNotEmpty()) currencyKeys[itemPosition.intValue] else "",
                    color = if (isDark == false) MaterialTheme.colorScheme.tertiary
                        else MaterialTheme.colorScheme.secondary
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = if (isDark == false) MaterialTheme.colorScheme.tertiary
                        else MaterialTheme.colorScheme.secondary
                )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                currencyKeys.forEachIndexed { index, currency ->
                    DropdownMenuItem(text = {
                        Text(
                            text =
                            currency, color = MaterialTheme.colorScheme.secondary
                        )
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.intValue = index
                            onChange(currency)
                        },
                        modifier = Modifier.testTag(testTag + index)
                    )
                }
            }
        }
    }
}
