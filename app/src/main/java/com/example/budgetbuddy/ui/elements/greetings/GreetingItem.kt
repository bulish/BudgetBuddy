package com.example.budgetbuddy.ui.elements.greetings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.ImageSize

@Composable
fun ItemGreeting(item: Triple<Int, String, String>) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(item.first))

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = BasicMargin(), horizontal = BasicMargin()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(ImageSize())
        )

        Spacer(modifier = Modifier.height(BasicMargin()))

        Text(
            text = item.second,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(BasicMargin()))

        Text(
            text = item.third,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
