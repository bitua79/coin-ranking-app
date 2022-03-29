package com.example.coinRankingUpdate.data.repository

import androidx.lifecycle.LiveData
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.model.Cryptocurrency

interface CryptocurrencyRepository {
    suspend fun getAllCryptocurrencies(): LiveData<Resource<List<Cryptocurrency>>>
}