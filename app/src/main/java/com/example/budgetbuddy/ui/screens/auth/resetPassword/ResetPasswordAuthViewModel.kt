package com.example.budgetbuddy.ui.screens.auth.resetPassword

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.NotificationData
import com.example.budgetbuddy.services.datastore.IDataStoreRepository
import com.example.budgetbuddy.utils.ErrorUtils
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
class ResetPasswordAuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val dataStoreRepository: IDataStoreRepository
) : ViewModel(), ResetPasswordActions {

    private val _resetEmailUIState: MutableStateFlow<ResetPasswordUIState> =
        MutableStateFlow(value = ResetPasswordUIState.Default)

    val resetEmailUIState = _resetEmailUIState.asStateFlow()

    private var data = ResetPasswordData()

    override fun onEmailChanged(email: String) {
        data.email = email
        _resetEmailUIState.update {
            ResetPasswordUIState.EmailChanged(data)
        }
    }

    override fun sendEmail() {
        val email = data.email

        if (email.isNotEmpty()) {
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    try {
                        if (task.isSuccessful) {
                            _resetEmailUIState.update {
                                ResetPasswordUIState.EmailSent
                            }
                            viewModelScope.launch {
                                dataStoreRepository.saveNotificationData(
                                    NotificationData(
                                        show = true,
                                        message = R.string.reset_success,
                                        isSuccess = true
                                    )
                                )
                            }

                            Log.d("ResetPasswordAuthViewModel", "sendPasswordResetEmail:success")
                        } else {
                            Log.e(
                                "ResetPasswordAuthViewModel",
                                "sendPasswordResetEmail:failure",
                                task.exception
                            )

                            _resetEmailUIState.update {
                                ResetPasswordUIState.EmailChanged(data)
                            }

                            val exception = task.exception
                            if (exception is FirebaseAuthException) {
                                viewModelScope.launch {
                                    dataStoreRepository.saveNotificationData(
                                        NotificationData(
                                            show = true,
                                            message = ErrorUtils.handleFirebaseError(exception.errorCode),
                                            isSuccess = false
                                        )
                                    )
                                }
                            } else if (exception is FirebaseNetworkException) {
                                viewModelScope.launch {
                                    dataStoreRepository.saveNotificationData(
                                        NotificationData(
                                            show = true,
                                            message = R.string.network_error,
                                            isSuccess = false
                                        )
                                    )
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(
                            "ResetPasswordAuthViewModel",
                            "sendPasswordResetEmail:exception",
                            e
                        )

                        if (e is FirebaseNetworkException) {
                            viewModelScope.launch {
                                dataStoreRepository.saveNotificationData(
                                    NotificationData(
                                        show = true,
                                        message = R.string.network_error,
                                        isSuccess = false
                                    )
                                )
                            }
                        } else {
                            viewModelScope.launch {
                                dataStoreRepository.saveNotificationData(
                                    NotificationData(
                                        show = true,
                                        message = ErrorUtils.handleFirebaseError((task.exception as? FirebaseAuthException)?.errorCode ?: ""),
                                        isSuccess = false
                                    )
                                )
                            }
                        }
                    }
                }
        } else {
            _resetEmailUIState.update {
                ResetPasswordUIState.EmailChanged(data)
            }

            if (email.isEmpty()) {
                data.emailError = R.string.cannot_be_empty
            }
        }
    }
}
