package com.example.budgetbuddy.ui.screens.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.R
import com.example.budgetbuddy.services.AuthService
import com.example.budgetbuddy.services.IAuthService
import com.example.budgetbuddy.services.UserData
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import com.example.budgetbuddy.utils.ErrorUtils.handleFirebaseError
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginAuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val authService: IAuthService,
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel(), LoginActions {
    private val _loginUIState: MutableStateFlow<LoginUIState> =
        MutableStateFlow(value = LoginUIState.Default)

    val loginUIState = _loginUIState.asStateFlow()

    private var data = LoginData()

    init {
        if (authService.getCurrentUser() != null) {
            _loginUIState.update {
                LoginUIState.UserLoggedIn(
                    authService.getCurrentUser(), null)
            }
        } else {
            _loginUIState.update {
                LoginUIState.UserChanged(data)
            }
        }
    }

    override fun loginUser() {
        val email = data.user.email
        val password = data.user.password

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        _loginUIState.update {
                            LoginUIState.UserLoggedIn(UserData.Firebase(user), R.string.login_success)
                        }
                    } else {
                        Log.e(
                            "LoginAuthViewModel",
                            "signInWithEmailAndPassword:failure",
                            task.exception
                        )

                        _loginUIState.update {
                            LoginUIState.UserChanged(data)
                        }

                        val exception = task.exception
                        if (exception is FirebaseAuthException) {
                            _loginUIState.update {
                                LoginUIState.Error(handleFirebaseError(exception.errorCode))
                            }
                        } else if (exception is FirebaseNetworkException) {
                            _loginUIState.update {
                                LoginUIState.Error(R.string.network_error)
                            }
                        } else {
                            _loginUIState.update {
                                LoginUIState.Error(R.string.network_error)
                            }
                        }
                    }
                }
        } else {
            _loginUIState.update {
                LoginUIState.UserChanged(data)
            }

            if (email.isEmpty()) {
                data.userEmailError = R.string.cannot_be_empty
            }

            if (password.isEmpty()) {
                data.userPasswordError = R.string.cannot_be_empty
            }
        }
    }


    override fun onUserEmailChanged(email: String) {
        data.user.email = email
        _loginUIState.update {
            LoginUIState.UserChanged(data)
        }
    }

    override fun onUserPasswordChanged(password: String) {
        data.user.password = password
        _loginUIState.update {
            LoginUIState.UserChanged(data)
        }
    }
}
