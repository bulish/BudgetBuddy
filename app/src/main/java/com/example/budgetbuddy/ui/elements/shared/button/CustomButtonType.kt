package com.example.budgetbuddy.ui.elements.shared.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.Green
import com.example.budgetbuddy.ui.theme.LightGrey
import com.example.budgetbuddy.ui.theme.White
import com.example.budgetbuddy.ui.theme.Black

enum class CustomButtonType(
    val modifier: Modifier,
    val contentColor: Color,
    val containerColor: Color,
    val strokeColor: Color
) {
    OutlinedMaxSize(
        modifier = Modifier.fillMaxWidth().padding(horizontal = BasicMargin()),
        contentColor = White,
        containerColor = Green,
        strokeColor = Green
    ),
    OutlinedMaxSizeLight(
        modifier = Modifier.fillMaxWidth().padding(horizontal = BasicMargin()),
        contentColor = Black,
        containerColor = White,
        strokeColor = LightGrey
    ),
    Basic(
        modifier = Modifier,
        contentColor = White,
        containerColor = Green,
        strokeColor = Green
    ),
    Outlined(
        modifier = Modifier,
        contentColor = Black,
        containerColor = White,
        strokeColor = Black
    )
}
