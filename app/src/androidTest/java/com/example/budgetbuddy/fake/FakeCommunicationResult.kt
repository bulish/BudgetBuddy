package com.example.budgetbuddy.fake

interface ICommunicationResult {
    fun getResult(): String
}

// Class Implementation
class CommunicationResult : ICommunicationResult {
    override fun getResult(): String = "Success"
}
