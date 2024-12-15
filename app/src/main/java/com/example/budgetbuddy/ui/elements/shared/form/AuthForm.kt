package com.example.budgetbuddy.ui.elements.shared.form

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.ui.elements.shared.button.CustomButton
import com.example.budgetbuddy.ui.elements.shared.button.CustomButtonType
import com.example.budgetbuddy.ui.elements.shared.button.getCustomButtonType
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.BorderWidth
import com.example.budgetbuddy.ui.theme.DoubleMargin
import com.example.budgetbuddy.ui.theme.Grey

@Composable
fun AuthForm(
    paddingValues: PaddingValues,
    title: String,
    inputs: @Composable () -> Unit,
    buttonText: String,
    onSubmit: () -> Unit,
    formBottomText: (@Composable () -> Unit)? = null,
    bottomBottomText: @Composable () -> Unit,
    testTag: String,
    submitButtonTestTag: String
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(
                start = BasicMargin(),
                end = BasicMargin(),
                bottom = BasicMargin(),
                top = 88.dp
            )
            .testTag(testTag),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderWidth(), Grey, RoundedCornerShape(BasicMargin()))
                        .padding(horizontal = BasicMargin())
                        .padding(top = DoubleMargin(), bottom = BasicMargin()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = title.uppercase(),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(BasicMargin()))

                    inputs()

                    Spacer(modifier = Modifier.height(BasicMargin()))

                    CustomButton(
                        type = getCustomButtonType(buttonType = CustomButtonType.Basic),
                        text = buttonText,
                        onClickAction = {
                            onSubmit()
                        },
                        testTag = submitButtonTestTag
                    )

                    Spacer(modifier = Modifier.height(BasicMargin()))

                    if (formBottomText != null) {
                        formBottomText()
                    }
                }
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                contentAlignment = Alignment.BottomCenter
            ) {
                bottomBottomText()
            }
        }
    }
}
