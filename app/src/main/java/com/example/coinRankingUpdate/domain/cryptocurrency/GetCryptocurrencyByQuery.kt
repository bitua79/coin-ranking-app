package com.example.coinRankingUpdate.domain.cryptocurrency

import androidx.lifecycle.LiveData
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity
import com.example.coinRankingUpdate.data.repository.CryptocurrencyRepository
import javax.inject.Inject

class GetCryptocurrencyByQuery @Inject constructor(private val repo: CryptocurrencyRepository) {
    suspend operator fun invoke(query: String): LiveData<Resource<List<CryptocurrencyEntity>>> {
        return repo.getCryptocurrenciesByQuery(query)
    }
}