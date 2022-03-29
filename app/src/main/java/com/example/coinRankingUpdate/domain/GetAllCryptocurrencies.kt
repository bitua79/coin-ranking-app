package com.example.coinRankingUpdate.domain

import androidx.lifecycle.LiveData
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.model.Cryptocurrency
import com.example.coinRankingUpdate.data.repository.CryptocurrencyRepository
import javax.inject.Inject

class GetAllCryptocurrencies @Inject constructor(
    private val repo: CryptocurrencyRepository
) {
    suspend operator fun invoke(): LiveData<Resource<List<Cryptocurrency>>> {
        return repo.getAllCryptocurrencies()
    }
}