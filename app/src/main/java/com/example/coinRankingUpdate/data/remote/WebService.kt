package com.example.coinRankingUpdate.data.remote

import com.example.coinRankingUpdate.core.entity.APIResponse
import com.example.coinRankingUpdate.data.entity.Cryptocurrencies
import retrofit2.Response
import retrofit2.http.GET

interface WebService {
    @GET("coins")
    suspend fun getAllCryptocurrencies(
//        @Query("timePeriod") timePeriod: String,
//        @Query("orderBy") orderBy: String,
//        @Query("orderDirection") orderDirection: String
    ): Response<APIResponse<Cryptocurrencies>>
}