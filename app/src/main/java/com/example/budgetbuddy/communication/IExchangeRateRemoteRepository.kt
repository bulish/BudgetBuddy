package com.example.budgetbuddy.communication

interface IExchangeRateRemoteRepository: IBaseRemoteRepository {
    suspend fun getCurrentCurrency(currency: String): CommunicationResult<ExchangeRateResponse>
}
