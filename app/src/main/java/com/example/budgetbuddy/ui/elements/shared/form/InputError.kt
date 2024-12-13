package com.example.budgetbuddy.ui.elements.shared.form

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Popup
import com.example.budgetbuddy.R
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.DoubleMargin
import com.example.budgetbuddy.ui.theme.HalfMargin
import com.example.budgetbuddy.ui.theme.Pink40
import com.example.budgetbuddy.ui.theme.QuarterMargin

@Composable
fun InputError(error: Int?) {
    var isTooltipVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.size(BasicMargin())
    ) {
        IconButton(onClick = { isTooltipVisible = !isTooltipVisible }) {
            Icon(
                painter = painterResource(id = R.drawable.error_24px),
                contentDescription = "Error",
                tint = Color.White,
                modifier = Modifier
                    .background(Color.Red, shape = CircleShape)
                    .size(BasicMargin())
            )
        }
        if (isTooltipVisible && error != null) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = { isTooltipVisible = false }
            ) {
                Box(
                    modifier = Modifier
                        .padding(HalfMargin())
                        .padding(end = BasicMargin())
                        .background(Color.Red, shape = CircleShape)
                        .padding(horizontal = BasicMargin(), vertical = QuarterMargin())
                ) {
                    Text(
                        text = stringResource(id = error),
                        color = Color.White
                    )
                }
            }
        }
    }
}
