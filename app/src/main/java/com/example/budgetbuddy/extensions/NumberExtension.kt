package com.example.budgetbuddy.extensions

fun Double.round(): String {
    return String.format("%.2f", this)
}
