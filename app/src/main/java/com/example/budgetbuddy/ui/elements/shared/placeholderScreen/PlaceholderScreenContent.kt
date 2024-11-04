package com.example.budgetbuddy.ui.elements.shared.placeholderScreen

data class PlaceholderScreenContent(
    val image: Int?,
    val title: String?,
    val text: String?,
    val buttonText: String? = null,
    val onButtonClick: (() -> Unit)? = null
)
