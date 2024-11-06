package com.example.budgetbuddy.utils

import com.example.budgetbuddy.R

object ErrorUtils {
    val firebaseErrors = mapOf(
        "ERROR_ARGUMENT_ERROR" to R.string.error_argument_error,
        "ERROR_APP_NOT_AUTHORIZED" to R.string.error_app_not_authorized01,
        "ERROR_EMAIL_ALREADY_IN_USE" to R.string.error_email_already_in_use,
        "ERROR_EMAIL_NOT_FOUND" to R.string.error_email_not_found,
        "ERROR_INVALID_EMAIL" to R.string.error_invalid_email,
        "ERROR_INVALID_CREDENTIAL" to R.string.error_invalid_credentials,
        "ERROR_MISSING_EMAIL" to R.string.error_missing_email,
        "ERROR_OPERATION_NOT_ALLOWED" to R.string.error_operation_not_allowed,
        "ERROR_PASSWORD_LOGIN_DISABLED" to R.string.error_password_login_disabled,
        "ERROR_TOO_MANY_ATTEMPTS_TRY_LATER" to R.string.error_too_many_attempts_try_later,
        "ERROR_USER_DISABLED" to R.string.error_user_disabled,
        "ERROR_USER_NOT_FOUND" to R.string.error_user_not_found,
        "ERROR_WEAK_PASSWORD" to R.string.error_weak_password
    )

    fun handleFirebaseError(errorCode: String) : Int {
        val errorMessage = firebaseErrors[errorCode]
        if (errorMessage != null) {
            return errorMessage
        } else {
            return 0
        }
    }
}
