package com.example.budgetbuddy.fake

interface ICommunicationResult {
    fun getResult(): String
}

class CommunicationResult : ICommunicationResult {
    override fun getResult(): String = "Success"
}
