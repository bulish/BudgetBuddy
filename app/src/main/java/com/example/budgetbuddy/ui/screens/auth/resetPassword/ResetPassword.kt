package com.example.budgetbuddy.ui.screens.auth.resetPassword

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.budgetbuddy.R
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.shared.ShowToast
import com.example.budgetbuddy.ui.elements.shared.basescreen.BaseScreen
import com.example.budgetbuddy.ui.elements.shared.form.AuthForm
import com.example.budgetbuddy.ui.elements.shared.form.FormPageTopAppBar
import com.example.budgetbuddy.ui.elements.shared.form.TextInput

@Composable
fun ResetPasswordScreen(
    navigationRouter: INavigationRouter,
) {
    val viewModel = hiltViewModel<ResetPasswordAuthViewModel>()
    val state = viewModel.resetEmailUIState.collectAsState()

    var data by remember {
        mutableStateOf(ResetPasswordData())
    }

    state.value.let {
        when (it) {
            ResetPasswordUIState.Default -> {
                //
            }

            is ResetPasswordUIState.EmailChanged -> {
                data = it.data
            }

            is ResetPasswordUIState.EmailSent -> {
                ShowToast(message = stringResource(id = it.message))

                LaunchedEffect(it) {
                    navigationRouter.returnBack()
                }
            }

            is ResetPasswordUIState.Error -> {
                ShowToast(message = stringResource(id = it.message))
            }
        }
    }

    BaseScreen(
        topBar = {
            FormPageTopAppBar(
                title = stringResource(id = R.string.reset_password_title),
                subtitle = stringResource(id = R.string.reset_password_subtitle)
            )
        },
        isAuth = true,
        navigation = navigationRouter,
        navigationTitleTestTag = TestTagResetPasswordScreenTitle
    ) {
        ResetPasswordContent(
            paddingValues = it,
            data = data,
            actions = viewModel,
            navigationRouter = navigationRouter
        )
    }
}

@Composable
fun ResetPasswordContent(
    paddingValues: PaddingValues,
    data: ResetPasswordData,
    actions: ResetPasswordActions,
    navigationRouter: INavigationRouter,
) {

    AuthForm(
        paddingValues = paddingValues,
        title = stringResource(id = R.string.reset_form_title),
        inputs = {
            TextInput(
                label = "Email",
                value = data.email,
                error = data.emailError,
                onChange = { actions.onEmailChanged(it) },
                testTag = TestTagResetPasswordScreenEmailInput
            )
        },
        buttonText = stringResource(id = R.string.reset_submit),
        onSubmit = {
            actions.sendEmail()
        },
        bottomBottomText = {
            Text(
                text = stringResource(id = R.string.reset_back),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.clickable() {
                    navigationRouter.navigateToLoginScreen()
                }.testTag(TestTagResetPasswordScreenGoBackButton)
            )
        },
        testTag = TestTagResetPasswordScreenForm,
        submitButtonTestTag = TestTagResetPasswordScreenSubmitButton
    )
}
