package com.example.budgetbuddy.ui.screens.auth.signUp

import com.example.budgetbuddy.model.UserSignUp

class SignUpData {

    var user: UserSignUp = UserSignUp("", "", "", "")
    var userEmailError: Int? = null
    var userPasswordError: Int? = null
    var userPasswordAgainError: Int? = null
    var userUsernameError: Int? = null

}
