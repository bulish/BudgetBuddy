package com.example.budgetbuddy.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.elements.shared.BaseScreen

@Composable
fun HomeScreen(
    navigationRouter: INavigationRouter
) {
    BaseScreen(navigation = navigationRouter) {
        HomeScreenContent(it)
    }
}

@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        Text("Hello, I am welcome screen")
    }
}
