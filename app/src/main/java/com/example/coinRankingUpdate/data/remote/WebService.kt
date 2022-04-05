package com.example.coinRankingUpdate.data.remote

import com.example.coinRankingUpdate.core.entity.APIResponse
import com.example.coinRankingUpdate.data.entity.CryptocurrenciesResponse
import com.example.coinRankingUpdate.data.entity.CryptocurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {
    @GET("coins")
    suspend fun getAllCryptocurrencies(
        @Query("timePeriod") timePeriod: String,
        @Query("orderBy") orderBy: String,
        @Query("orderDirection") orderDirection: String
    ): Response<APIResponse<CryptocurrenciesResponse>>

    @GET("coin/{uuid}")
    suspend fun getCryptocurrency(
        @Path("uuid") uuid: String,
//        @Query("timePeriod") timePeriod: String
    ): Response<APIResponse<CryptocurrencyResponse>>
}