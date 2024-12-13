package com.example.budgetbuddy.communication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface CurrencyAPI {
    @Headers("Content-Type: application/json")
    @GET("latest/{currency}")
    suspend fun getCurrentCurrency(
        @Path("currency") currency: String
    ): Response<ExchangeRateResponse>
}
