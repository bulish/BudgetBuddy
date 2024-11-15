package com.example.budgetbuddy.ui.elements.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.budgetbuddy.R
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.shared.placeholderScreen.PlaceHolderScreen
import com.example.budgetbuddy.ui.elements.shared.placeholderScreen.PlaceholderScreenContent
import com.example.budgetbuddy.ui.elements.shared.navigation.BottomNavigationBar

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
    navigation: INavigationRouter,
    content: @Composable (paddingValues: PaddingValues) -> Unit) {

    Scaffold(
        modifier = Modifier.padding(top = if (hideNavigation == true) 48.dp else 0.dp),
        bottomBar = {
            if (hideNavigation == false) {
                BottomAppBar(modifier = Modifier) {
                    BottomNavigationBar(navigation = navigation)
                }
            }
        },
        floatingActionButton = floatingActionButton,
        topBar = {
            if (topBar != null) {
                TopAppBar(
                    title = {
                        if (topBarText != null) {
                            Text(
                                text = topBarText,
                                style = MaterialTheme.typography.titleLarge,
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
    }
}
