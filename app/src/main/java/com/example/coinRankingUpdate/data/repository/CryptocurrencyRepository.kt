package com.example.coinRankingUpdate.data.repository

import androidx.lifecycle.LiveData
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.Cryptocurrency

interface CryptocurrencyRepository {
    fun getAllCryptocurrencies(): LiveData<Resource<List<Cryptocurrency>>>

    fun getCryptocurrency( id: String): LiveData<Resource<Cryptocurrency>>
}