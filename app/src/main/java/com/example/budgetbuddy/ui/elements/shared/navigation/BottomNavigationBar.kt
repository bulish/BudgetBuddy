package com.example.budgetbuddy.ui.elements.shared.navigation

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.budgetbuddy.navigation.Destination
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.theme.HalfMargin
import com.example.budgetbuddy.ui.theme.White

@Composable
fun BottomNavigationBar(navigation: INavigationRouter) {
    val items = listOf(
        Destination.HomeScreen,
        Destination.TransactionsList,
        Destination.MapScreen,
        Destination.SettingsScreen
    )

    val navController = navigation.getNavController()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val selectedItem = remember(currentRoute) {
        Log.d("current route", "$currentRoute")
        items.indexOfFirst { it.route == currentRoute }
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                alwaysShowLabel = true,
                icon = {
                    Column {
                        Icon(
                            item.icon ?: Icons.Default.Home,
                            contentDescription = stringResource(id = item.title),
                            tint = if (selectedItem == index) White else MaterialTheme.colorScheme.secondary
                        )

                    }
                },
                label = {
                    Text(
                        stringResource(id = item.title),
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.offset(y = HalfMargin())
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    navigation.navigateTabs(item.route)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = if (selectedItem == index) MaterialTheme.colorScheme.primary else White,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.background,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}
