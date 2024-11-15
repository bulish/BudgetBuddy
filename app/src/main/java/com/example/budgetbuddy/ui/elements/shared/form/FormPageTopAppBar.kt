package com.example.budgetbuddy.ui.elements.shared.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.budgetbuddy.ui.theme.DoubleMargin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPageTopAppBar(title: String, subtitle: String) {
    TopAppBar(title = {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    },
        colors = MaterialTheme.colorScheme.run {
            TopAppBarDefaults.smallTopAppBarColors(containerColor = background)
        },
        modifier = Modifier.padding(vertical = DoubleMargin()))
}
