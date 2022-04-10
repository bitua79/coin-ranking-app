package com.example.coinRankingUpdate.data.repository

import androidx.lifecycle.LiveData
import com.example.coinRankingUpdate.core.entity.OrderBy
import com.example.coinRankingUpdate.core.entity.OrderDirection
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.Cryptocurrency

interface CryptocurrencyRepository {
    fun getAllCryptocurrencies(
        timePeriod: String,
        orderBy: OrderBy,
        orderDirection: OrderDirection
    ): LiveData<Resource<List<Cryptocurrency>>>

    fun getCryptocurrency(
        id: String,
        timePeriod: String
    ): LiveData<Resource<Cryptocurrency>>

    suspend fun getCryptocurrenciesByQuery(
        query: String
    ): LiveData<Resource<List<Cryptocurrency>>>
}