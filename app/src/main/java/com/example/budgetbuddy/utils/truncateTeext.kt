package com.example.budgetbuddy.utils

fun truncateText(text: String, maxLength: Int): String {
    return if (text.length > maxLength) {
        "${text.substring(0, maxLength)}..."
    } else {
        text
    }
}
