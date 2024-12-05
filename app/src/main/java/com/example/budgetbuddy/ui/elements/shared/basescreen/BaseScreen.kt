package com.example.budgetbuddy.ui.elements.shared.basescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.budgetbuddy.R
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.shared.LoadingScreen
import com.example.budgetbuddy.ui.elements.shared.placeholderScreen.PlaceHolderScreen
import com.example.budgetbuddy.ui.elements.shared.placeholderScreen.PlaceholderScreenContent
import com.example.budgetbuddy.ui.elements.shared.navigation.BottomNavigationBar
import com.example.budgetbuddy.ui.elements.shared.notification.Notification

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    topBar: (@Composable () -> Unit)? = null,
    topBarText: String? = null,
    onBackClick: (() -> Unit)? = null,
    placeholderScreenContent: PlaceholderScreenContent? = null,
    showLoading: Boolean = false,
    floatingActionButton: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    hideNavigation: Boolean? = false,
    isAuth: Boolean? = false,
    navigation: INavigationRouter,

    content: @Composable (paddingValues: PaddingValues) -> Unit) {

    val viewModel = hiltViewModel<BaseScreenViewModel>()
    val notificationData = viewModel.notificationData.collectAsState()

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(top = if (isAuth == true) 48.dp else 0.dp),
        bottomBar = {
            if (hideNavigation == false && isAuth == false) {
                BottomAppBar(modifier = Modifier) {
                    BottomNavigationBar(navigation = navigation)
                }
            }
        },
        floatingActionButton = floatingActionButton,
        topBar = {
            if (topBar != null || topBarText != null ) {
                TopAppBar(
                    title = {
                        if (topBarText != null) {
                            Text(
                                text = topBarText,
                                style = MaterialTheme.typography.headlineLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .padding(start = 0.dp)
                            )
                        } else if (topBar != null) {
                            topBar()
                        }

                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    ),
                    actions = actions,
                    navigationIcon = {
                        if (onBackClick != null) {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = stringResource(
                                        R.string.back
                                    ),
                                )
                            }
                        }
                    }
                )
            }
        }
    ) {
        if (placeholderScreenContent != null) {
            PlaceHolderScreen(
                content = placeholderScreenContent
            )
        } else if (showLoading) {
            LoadingScreen()
        } else {
            content(it)
        }

        Notification(data = notificationData.value)
    }
}
