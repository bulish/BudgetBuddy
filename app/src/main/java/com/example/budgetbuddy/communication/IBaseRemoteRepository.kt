package com.example.budgetbuddy.communication

import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

interface IBaseRemoteRepository {
    fun <T : Any> handleResponse(call: Response<T>): CommunicationResult<T> {
        try {
            if (call.isSuccessful) {
                call.body()?.let {
                    return CommunicationResult.Success(it)
                }?:run {
                    return CommunicationResult.Error(CommunicationError(code = call.code(), message = null))
                }
            } else {
                return CommunicationResult.Error(CommunicationError(call.code(), call.message()))
            }
        } catch (ex: UnknownHostException) {
            return CommunicationResult.Exception(ex)
        } catch (ex: SocketTimeoutException){
            return CommunicationResult.ConnectionError()
        } catch (ex: Exception){
            return CommunicationResult.Exception(ex)
        }

    }

    fun processException(ex: Exception): CommunicationResult<Nothing> {
        return when (ex){
            is UnknownHostException -> {
                CommunicationResult.Exception(ex)
            }
            is SocketTimeoutException -> {
                CommunicationResult.ConnectionError()
            }
            else -> {
                CommunicationResult.Exception(ex)
            }
        }
    }


}

