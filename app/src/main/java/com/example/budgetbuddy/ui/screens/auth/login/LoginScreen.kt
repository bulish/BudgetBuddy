package com.example.budgetbuddy.ui.screens.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import com.example.budgetbuddy.ui.elements.shared.form.FormBottomText
import com.example.budgetbuddy.ui.elements.shared.form.FormPageTopAppBar
import com.example.budgetbuddy.ui.elements.shared.form.PasswordInput
import com.example.budgetbuddy.ui.elements.shared.form.TextInput
import com.example.budgetbuddy.ui.theme.BasicMargin

@Composable
fun LoginScreen(navigationRouter: INavigationRouter) {
    val viewModel = hiltViewModel<LoginAuthViewModel>()
    val state = viewModel.loginUIState.collectAsState()

    var data by remember {
        mutableStateOf(LoginData())
    }

    var loading = true

    state.value.let {
        when (it) {
            LoginUIState.Default -> {
                loading = true
            }

            is LoginUIState.UserChanged -> {
                data = it.data
                loading = false
            }

            is LoginUIState.UserLoggedIn -> {
                if (it.message != null) {
                    ShowToast(message = stringResource(id = it.message))
                }

                loading = true
                LaunchedEffect(it) {
                    navigationRouter.navigateToHomeScreen()
                }
            }

            is LoginUIState.Error -> {
                loading = false
                ShowToast(message = stringResource(id = it.message))
            }
        }
    }

    BaseScreen(
        topBar = {
            FormPageTopAppBar(
                title = stringResource(id = R.string.login_title),
                subtitle = stringResource(id = R.string.login_subtitle)
            )
        },
        isAuth = true,
        navigation = navigationRouter,
        showLoading = loading
    ) {
        LoginScreenContent(
            paddingValues = it,
            data = data,
            actions = viewModel,
            navigationRouter = navigationRouter
        )
    }
}

@Composable
fun LoginScreenContent(
    paddingValues: PaddingValues,
    data: LoginData,
    actions: LoginActions,
    navigationRouter: INavigationRouter,
) {
    AuthForm(
        paddingValues = paddingValues,
        title = stringResource(id = R.string.login_form_title),
        inputs = {
            TextInput(
                label = "Email",
                value = data.user.email,
                error = data.userEmailError,
                onChange = { actions.onUserEmailChanged(it) },
                testTag = TestTagLoginScreenEmailInput
            )

            PasswordInput(
                value = data.user.password,
                onChange = { newPassword ->
                    actions.onUserPasswordChanged(newPassword)
                },
                error = data.userPasswordError,
                testTag = TestTagLoginScreenPasswordInput
            )
        },
        buttonText = stringResource(id = R.string.login_submit),
        onSubmit = {
            actions.loginUser()
        },
        formBottomText = {
            Spacer(modifier = Modifier.height(BasicMargin()))

            Text(
                text = stringResource(id = R.string.login_forgotten_password),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.clickable() {
                    navigationRouter.navigateToResetPasswordScreen()
                }.testTag(TestTagLoginScreenForgottenPassword)
            )
        },
        bottomBottomText = {
            FormBottomText(
                question = stringResource(id = R.string.login_new_user),
                text = stringResource(id = R.string.signup_submit),
                onClickHandler = {
                    navigationRouter.navigateToSignUpScreen()
                },
                testTag = TestTagLoginScreenSignUpButton
            )
        },
        testTag = TestTagLoginScreenForm,
        submitButtonTestTag = TestTagLoginScreenSubmitButton
    )
}
