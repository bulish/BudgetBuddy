package com.example.budgetbuddy.ui.screens.auth.signUp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun SignUpScreen(
    navigationRouter: INavigationRouter,
) {
    val viewModel = hiltViewModel<SignUpAuthViewModel>()
    val state = viewModel.signUpUIState.collectAsState()

    var data by remember {
        mutableStateOf(SignUpData())
    }

    state.value.let {
        when (it) {
            is SignUpUIState.UserChanged -> {
                data = it.data
            }

            is SignUpUIState.Default -> {

            }

            is SignUpUIState.UserSaved -> {
                ShowToast(message = stringResource(id = it.message))
                LaunchedEffect(it) {
                    navigationRouter.returnBack()
                }
            }

            is SignUpUIState.Error -> {
                ShowToast(message = stringResource(id = it.message))
            }
        }
    }

    BaseScreen(
        topBar = {
            FormPageTopAppBar(
                title = stringResource(id = R.string.signup_title),
                subtitle = stringResource(id = R.string.signup_subtitle)
            )
        },
        isAuth = true,
        navigation = navigationRouter
    ) {
        SignUpScreenContent(
            paddingValues = it,
            data = data,
            actions = viewModel,
            navigationRouter = navigationRouter
        )
    }
}

@Composable
fun SignUpScreenContent(
    paddingValues: PaddingValues,
    data: SignUpData,
    actions: SignUpActions,
    navigationRouter: INavigationRouter,
) {

    AuthForm(
        paddingValues = paddingValues,
        title = stringResource(id = R.string.signup_form_title),
        inputs = {
            TextInput(
                label = "Email",
                value = data.user.email,
                error = data.userEmailError,
                onChange = { actions.onUserEmailChanged(it) }
            )

            TextInput(
                label = stringResource(id = R.string.placeholder_username),
                value = data.user.username,
                error = data.userUsernameError,
                onChange = { actions.onUserUsernameChanged(it) }
            )

            PasswordInput(
                value = data.user.password,
                onChange = { newPassword ->
                    actions.onUserPasswordChanged(newPassword)
                },
                error = data.userPasswordError
            )

            PasswordInput(
                value = data.user.passwordAgain,
                onChange = { newPassword ->
                    actions.onUserPasswordAgainChanged(newPassword)
                },
                error = data.userPasswordError
            )
        },
        buttonText = stringResource(id = R.string.signup_submit),
        onSubmit = {
            actions.saveUser()
        },
        bottomBottomText = {
            FormBottomText(
                question = stringResource(id = R.string.signup_already_have_aacount),
                text = stringResource(id = R.string.login_submit),
                onClickHandler = {
                    navigationRouter.navigateToLoginScreen()
                }
            )
        },
    )
}
