package com.example.budgetbuddy.ui.elements.shared.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.theme.Green

@Composable
fun BottomNavigationBar(navigation: INavigationRouter) {
    val items = listOf(
        Destination.HomeScreen,
        Destination.TransactionsList,
        Destination.MapScreen,
        Destination.SettingsScreen
    )

    var selectedItem by remember { mutableStateOf(0) }
    var currentRoute by remember { mutableStateOf(Destination.HomeScreen.route) }

    items.forEachIndexed { index, navigationItem ->
        if (navigationItem.route == currentRoute) {
            selectedItem = index
        }
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        item.icon ?: Icons.Default.Home,
                        contentDescription = item.title,
                        tint = if (selectedItem == index) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground
                    )
                },
                label = {
                    Text(
                        item.title,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    currentRoute = item.route
                    navigation.navigateTabs(item.route)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = if (selectedItem == index) Green else MaterialTheme.colorScheme.background,
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}
