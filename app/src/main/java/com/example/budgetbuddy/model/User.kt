package com.example.budgetbuddy.model

data class UserSignUp(
    var email: String,
    var password: String,
    var username: String,
    var passwordAgain: String
)

data class UserLogin(
    var email: String,
    var password: String,
)
