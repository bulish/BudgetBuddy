package com.example.budgetbuddy.ui.elements.shared

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CurrencyDropdown(
    currency: String,
    onChange: (currency: String) -> Unit,
    isDark: Boolean? = false
) {
    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val currencies = listOf("CZK (Kč)", "USD ($)", "EUR (€)", "GBP (£)")

    val itemPosition = remember {
        mutableStateOf(currencies.indexOf(currency).takeIf { it >= 0 } ?: 0)
    }

    Column(
        modifier = Modifier.then(
            if (isDark == false) Modifier.fillMaxWidth() else Modifier
        ),
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
                    text = currencies[itemPosition.value],
                    color = if (isDark == false) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = if (isDark == false) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary
                )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                currencies.forEachIndexed { index, currency ->
                    DropdownMenuItem(text = {
                        Text(
                            text =
                            currency, color = MaterialTheme.colorScheme.secondary
                        )
                    },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.value = index
                            onChange(currency)
                        })
                }
            }
        }

    }
}