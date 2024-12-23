package com.example.budgetbuddy.extensions

import android.annotation.SuppressLint

fun Double.round(): String {
    return String.format("%.2f", this)
}

@SuppressLint("DefaultLocale")
fun Double.toFormattedString(): String {
    val formatted = if (this % 1.0 == 0.0) {
        String.format("%,.0f", this)
    } else {
        String.format("%,.1f", this)
    }
    return formatted.replace(',', '.')
}
