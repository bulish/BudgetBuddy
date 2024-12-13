package com.example.budgetbuddy.communication

data class CommunicationError(
    val code: Int,
    val message: String? = null
)
