package com.example.budgetbuddy.communication

import javax.inject.Inject

class ExchangeRateRemoteRepositoryImpl @Inject constructor(
    private val currencyAPI: CurrencyAPI
) : IExchangeRateRemoteRepository {

    override suspend fun getCurrentCurrency(
        currency: String
    ):CommunicationResult<ExchangeRateResponse> {
        return try {
            handleResponse(currencyAPI.getCurrentCurrency(currency))
        } catch (ex: Exception){
            processException(ex)
        }
    }

}
