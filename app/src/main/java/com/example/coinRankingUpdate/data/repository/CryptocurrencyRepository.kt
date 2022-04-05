package com.example.coinRankingUpdate.data.repository

import androidx.lifecycle.LiveData
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.Cryptocurrency
import com.example.coinRankingUpdate.ui.cryptocurrency.OrderBy
import com.example.coinRankingUpdate.ui.cryptocurrency.OrderDirection

interface CryptocurrencyRepository {
    fun getAllCryptocurrencies(
        timePeriod: String,
        orderBy: OrderBy,
        orderDirection: OrderDirection
    ): LiveData<Resource<List<Cryptocurrency>>>

    fun getCryptocurrency( id: String): LiveData<Resource<Cryptocurrency>>
}