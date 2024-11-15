package com.example.budgetbuddy.ui.screens.settings

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.budgetbuddy.R
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.shared.BaseScreen
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.google.firebase.auth.FirebaseUser
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.ui.elements.settings.settingsItem.SettingsItem
import com.example.budgetbuddy.ui.elements.settings.userIcon.UserIcon
import com.example.budgetbuddy.ui.elements.settings.userIcon.UserIconType
import com.example.budgetbuddy.ui.elements.shared.CurrencyDropdown
import com.example.budgetbuddy.ui.elements.shared.SectionTitle
import com.example.budgetbuddy.ui.elements.shared.button.CustomButton
import com.example.budgetbuddy.ui.elements.shared.button.CustomButtonType
import com.example.budgetbuddy.ui.elements.shared.form.CustomSwitch
import com.example.budgetbuddy.ui.theme.DoubleMargin
import com.example.budgetbuddy.ui.theme.HalfMargin

@Composable
fun SettingsScreen(
    navigationRouter: INavigationRouter,
    context: Context
) {
    val viewModel = hiltViewModel<SettingsViewModel>()
    val state = viewModel.settingsUIState.collectAsState()
    val currency = viewModel.activeCurrency.collectAsState()
    val isDarkMode = viewModel.isDarkMode.collectAsState()

    var version = ""
    var userData: MutableState<FirebaseUser?> = remember {
        mutableStateOf(null)
    }

    try {
        val pInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        version = pInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    var loading by remember { mutableStateOf(false) }

    state.value.let {
        when (it) {
            SettingsUIState.Loading -> {
                loading = true
                viewModel.getUserInformation()
            }

            SettingsUIState.UserNotAuthorized -> {
                loading = true
                navigationRouter.navigateToLoginScreen()
            }

            is SettingsUIState.Success -> {
                userData.value = it.user
                loading = false
            }
        }
    }

    BaseScreen(
        topBarText = stringResource(id = R.string.settings),
        showLoading = loading,
        navigation = navigationRouter
    ) {
        SettingsScreenContent(
            paddingValues = it,
            userData = userData.value,
            actions = viewModel,
            version = version,
            currency = currency.value,
            isDarkMode = isDarkMode.value
        )
    }
}

@Composable
fun SettingsScreenContent(
    paddingValues: PaddingValues,
    userData: FirebaseUser?,
    actions: SettingsActions,
    version: String,
    currency: String,
    isDarkMode: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .padding(top = HalfMargin())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    )
    {
        item {
            UserIcon(
                type = UserIconType.Large,
                userName = userData?.displayName,
                email = userData?.email
            )

            Spacer(modifier = Modifier.height(DoubleMargin()))

            Column(modifier = Modifier.padding(horizontal = BasicMargin())) {
                SectionTitle(
                    title = stringResource(id = R.string.settings_appearance),
                    horizontalPadding = 0.dp,
                    showDivider = true
                )

                SettingsItem(
                    title = stringResource(id = R.string.settings_dark_mode),
                    switchValue = isDarkMode,
                    onSwitchChange = {
                        actions.changeMode()
                    }
                )

                Row(
                    modifier = Modifier
                        .padding(vertical = HalfMargin())
                        .height(DoubleMargin()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.currency),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.weight(1.0f)
                    )
                    CurrencyDropdown(
                        currency = currency,
                        onChange = { selectedCurrency ->
                            actions.changeCurrency(selectedCurrency)
                        },
                        isDark = true
                    )
                }

                SettingsItem(
                    title = stringResource(id = R.string.settings_language),
                    content = stringResource(id = R.string.settings_activeLanguage)
                )

                SectionTitle(
                    title = stringResource(id = R.string.settings_application_info),
                    horizontalPadding = 0.dp,
                    showDivider = true
                )

                SettingsItem(
                    title = stringResource(id = R.string.settings_version),
                    content = version
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = BasicMargin()),
                contentAlignment = Alignment.BottomCenter
            ) {
                CustomButton(
                    type = CustomButtonType.OutlinedMaxSize,
                    text = stringResource(id = R.string.sign_out),
                    onClickAction = { actions.signOut() }
                )
            }
        }
    }
}

