package com.example.budgetbuddy.ui.elements.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.budgetbuddy.ui.theme.BasicMargin

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
){
    Box(modifier = modifier.fillMaxSize()) {
        /*CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = BasicMargin(), bottom = BasicMargin())
        )*/
        Text(text = "Loading")
    }
}
