package com.example.budgetbuddy.ui.screens.auth.signUp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.NotificationData
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import com.example.budgetbuddy.utils.ErrorUtils.handleFirebaseError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpAuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel(), SignUpActions {

    private val _signUpUIState: MutableStateFlow<SignUpUIState> =
        MutableStateFlow(value = SignUpUIState.Default)

    val signUpUIState = _signUpUIState.asStateFlow()
    private var data = SignUpData()

    override fun saveUser() {
        val email = data.user.email
        val username = data.user.username
        val password = data.user.password
        val password_again = data.user.passwordAgain

        if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()
            && password_again.isNotEmpty() && password == password_again) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val profileUpdates = userProfileChangeRequest {
                            displayName = username
                        }
                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { profileUpdateTask ->
                                if (profileUpdateTask.isSuccessful) {
                                    Log.d("SignUpAuthViewModel", "User profile updated.")
                                } else {
                                    Log.e(
                                        "SignUpAuthViewModel",
                                        "Failed to update user profile.",
                                        profileUpdateTask.exception
                                    )
                                }

                                viewModelScope.launch {
                                    val message =
                                        if (profileUpdateTask.isSuccessful) R.string.signup_success else handleFirebaseError(
                                            (profileUpdateTask.exception as FirebaseAuthException).errorCode
                                        )
                                    dataStoreRepository.saveNotificationData(
                                        NotificationData(
                                            show = true,
                                            message = message,
                                            isSuccess = profileUpdateTask.isSuccessful
                                        )
                                    )
                                }

                                _signUpUIState.update {
                                    SignUpUIState.UserSaved
                                }
                            }
                    } else {
                        Log.e(
                            "SignUpAuthViewModel",
                            "createUserWithEmailAndPassword:failure",
                            task.exception
                        )

                        viewModelScope.launch {
                            val exception = task.exception
                            if (exception is FirebaseAuthException) {
                                dataStoreRepository.saveNotificationData(
                                    NotificationData(
                                        show = true,
                                        message = handleFirebaseError(exception.errorCode),
                                        isSuccess = false
                                    )
                                )
                            } else if (exception is FirebaseNetworkException) {
                                dataStoreRepository.saveNotificationData(
                                    NotificationData(
                                        show = true,
                                        message = R.string.network_error,
                                        isSuccess = false
                                    )
                                )
                            }
                        }

                        _signUpUIState.update {
                            SignUpUIState.UserChanged(data)
                        }
                    }
                }
        } else {
            _signUpUIState.update {
                SignUpUIState.UserChanged(data)
            }

            if (email.isEmpty()) {
                data.userEmailError = R.string.cannot_be_empty
            }

            if (username.isEmpty()) {
                data.userUsernameError = R.string.cannot_be_empty
            }

            if (password.isEmpty()) {
                data.userPasswordError = R.string.cannot_be_empty
            }

            if (password_again.isEmpty()) {
                data.userPasswordError = R.string.cannot_be_empty
            }

            if (password != password_again) {
                data.userPasswordAgainError = R.string.passwords_dont_match
            }
        }
    }

    override fun onUserEmailChanged(email: String) {
        data.user.email = email
        _signUpUIState.update {
            SignUpUIState.UserChanged(data)
        }
    }

    override fun onUserPasswordChanged(password: String) {
        data.user.password = password
        _signUpUIState.update {
            SignUpUIState.UserChanged(data)
        }
    }

    override fun onUserPasswordAgainChanged(password: String) {
        data.user.passwordAgain = password
        _signUpUIState.update {
            SignUpUIState.UserChanged(data)
        }
    }

    override fun onUserUsernameChanged(username: String) {
        data.user.username = username
        _signUpUIState.update {
            SignUpUIState.UserChanged(data)
        }
    }
}
