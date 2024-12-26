package com.example.budgetbuddy.ui.elements.shared.form

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.example.budgetbuddy.R
import com.example.budgetbuddy.ui.elements.shared.CustomDivider
import com.example.budgetbuddy.ui.elements.shared.button.CustomButton
import com.example.budgetbuddy.ui.elements.shared.button.CustomButtonType
import com.example.budgetbuddy.ui.elements.shared.button.getCustomButtonType
import com.example.budgetbuddy.ui.theme.HalfMargin

@Composable
fun SaveCancelButtons(
    onSave: () -> Unit,
    onCancel: () -> Unit,
    saveButtonTestTag: String
) {
    CustomDivider()

    Row(modifier = Modifier
        .padding(top = HalfMargin())
        .testTag(saveButtonTestTag)
    ) {
        CustomButton(
            type = getCustomButtonType(buttonType = CustomButtonType.Basic),
            text = stringResource(id = R.string.save),
            onClickAction = { onSave() },
            testTag = saveButtonTestTag + "_submit"
        )

        Spacer(modifier = Modifier.width(HalfMargin()))

        CustomButton(
            type = getCustomButtonType(buttonType = CustomButtonType.Outlined),
            text = stringResource(id = R.string.cancel),
            onClickAction = { onCancel() },
            testTag = ""
        )
    }
}
