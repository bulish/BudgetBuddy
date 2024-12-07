package com.example.budgetbuddy.extensions

import java.time.LocalDateTime

fun LocalDateTime.toEpochMillis(): Long {
    return this.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun LocalDateTime.formatToDisplayString(): String {
    val formatter = java.time.format.DateTimeFormatter.ofPattern("MM/dd/yyyy")
    return this.format(formatter)
}
