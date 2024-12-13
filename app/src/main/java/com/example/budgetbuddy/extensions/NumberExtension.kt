package com.example.budgetbuddy.extensions

fun Double.round(): String {
    return String.format("%.2f", this)
}

fun Double.toFormattedString(): String {
    val formatted = String.format("%,.2f", this).replace(',', ' ')
    return if (this % 1.0 == 0.0) {
        formatted.substring(0, formatted.length - 3)
    } else {
        formatted
    }
}