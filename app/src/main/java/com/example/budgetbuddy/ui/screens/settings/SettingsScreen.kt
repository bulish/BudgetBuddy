package com.example.budgetbuddy.ui.screens.settings

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.budgetbuddy.R
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.shared.basescreen.BaseScreen
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.google.firebase.auth.FirebaseUser
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.model.PrimaryColor
import com.example.budgetbuddy.services.UserData
import com.example.budgetbuddy.ui.elements.settings.ColorDropdown
import com.example.budgetbuddy.ui.elements.settings.settingsItem.SettingsItem
import com.example.budgetbuddy.ui.elements.settings.userIcon.UserIcon
import com.example.budgetbuddy.ui.elements.settings.userIcon.UserIconType
import com.example.budgetbuddy.ui.elements.shared.CurrencyDropdown
import com.example.budgetbuddy.ui.elements.shared.SectionTitle
import com.example.budgetbuddy.ui.elements.shared.ShowToast
import com.example.budgetbuddy.ui.elements.shared.button.CustomButton
import com.example.budgetbuddy.ui.elements.shared.button.CustomButtonType
import com.example.budgetbuddy.ui.elements.shared.button.getCustomButtonType
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
    val primaryColor = viewModel.primaryColor.collectAsState()
    val userData = viewModel.userInfo.collectAsState()

    var version = ""

    try {
        val pInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        version = pInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    val currencies = remember {
        mutableStateOf<Map<String, Double>?>(null)
    }

    var loading by remember { mutableStateOf(false) }

    state.value.let {
        when (it) {
            SettingsUIState.Loading -> {
                loading = true
            }

            is SettingsUIState.UserNotAuthorized -> {
                loading = true
                navigationRouter.navigateToLoginScreen()
                if (it.message != null) {
                    ShowToast(message = stringResource(id = it.message))
                } else {
                    Log.e("SettingsScreen", "Not authorized")
                }
            }

            is SettingsUIState.Success -> {
                loading = false
                viewModel.getCurrencyData()
            }

            is SettingsUIState.CurrencyLoaded -> {
                currencies.value = it.data
                loading = false
            }

            is SettingsUIState.Error -> {
                ShowToast(stringResource(id = it.error.communicationError))
            }
        }
    }

    BaseScreen(
        topBarText = stringResource(id = R.string.settings),
        showLoading = loading,
        navigation = navigationRouter,
        navigationTitleTestTag = TestTagSettingsScreenTitle
    ) {
        SettingsScreenContent(
            paddingValues = it,
            userData = userData.value,
            actions = viewModel,
            version = version,
            currency = currency.value,
            isDarkMode = isDarkMode.value,
            primaryColor = primaryColor.value,
            currencies = currencies.value
        )
    }
}

@Composable
fun SettingsScreenContent(
    paddingValues: PaddingValues,
    userData: UserData?,
    actions: SettingsActions,
    version: String,
    currency: String,
    isDarkMode: Boolean,
    primaryColor: PrimaryColor,
    currencies: Map<String, Double>?
) {
    val updatedCurrency = remember { mutableStateOf(currency) }

    LaunchedEffect(updatedCurrency) {
        actions.getCurrencyData()
    }

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
                email = userData?.email,
                iconTestNode = TestTagSettingsScreenUserIcon,
                nameTestNode = TestTagSettingsScreenUserName
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
                    },
                    testTag = TestTagSettingsScreenModeToggle
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
                        currency = updatedCurrency.value,
                        onChange = { selectedCurrency ->
                            updatedCurrency.value = selectedCurrency
                            actions.changeCurrency(selectedCurrency)
                        },
                        isDark = true,
                        currencies = currencies,
                        testTag = TestTagSettingsScreenCurrencyDropdown
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(vertical = HalfMargin())
                        .height(DoubleMargin()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.main_color),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.secondary
                        ),
                        modifier = Modifier.weight(1.0f)
                    )

                    ColorDropdown(
                        color = primaryColor,
                        onChange = { selectedColor ->
                            actions.changePrimaryColor(selectedColor)
                        },
                        testTag = TestTagSettingsScreenColorDropdown
                    )
                }

                SettingsItem(
                    title = stringResource(id = R.string.settings_language),
                    content = stringResource(id = R.string.settings_activeLanguage),
                    testTag = TestTagSettingsScreenLanguage
                )

                SectionTitle(
                    title = stringResource(id = R.string.settings_application_info),
                    horizontalPadding = 0.dp,
                    showDivider = true
                )

                SettingsItem(
                    title = stringResource(id = R.string.settings_version),
                    content = version,
                    testTag = TestTagSettingsScreenVersion
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
                    type = getCustomButtonType(buttonType = CustomButtonType.OutlinedMaxSize),
                    text = stringResource(id = R.string.sign_out),
                    onClickAction = { actions.signOut() },
                    testTag = TestTagSettingsScreenLogOutButton
                )
            }
        }
    }
}

