package com.example.budgetbuddy.ui.elements.settings.userIcon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.HalfMargin

@Composable
fun UserIcon(
    type: UserIconType,
    userName: String?,
    email: String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(BasicMargin())
    ) {
        Box(
            modifier = Modifier
                .size(type.size),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(type.size)
                    .background(MaterialTheme.colorScheme.secondary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userName?.substring(0, 1)?.uppercase() ?: "X",
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = type.fontSize
                )
            }
        }

        if (type == UserIconType.Large && email != null) {
            Spacer(modifier = Modifier.height(HalfMargin()))
            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }
}

