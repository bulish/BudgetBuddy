package com.example.budgetbuddy.extensions

import java.time.LocalDateTime

fun Long.toLocalDateTime(): LocalDateTime {
    return java.time.Instant.ofEpochMilli(this).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime()
}

fun Long.toIntTimestamp(): Int {
    return (this / 1000).toInt()
}
