package com.example.coinRankingUpdate.domain.cryptocurrency

import androidx.lifecycle.LiveData
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity
import com.example.coinRankingUpdate.data.repository.CryptocurrencyRepository
import javax.inject.Inject

class GetCryptocurrency @Inject constructor(
    private val repo: CryptocurrencyRepository
) {
    operator fun invoke(
        id: String,
        timePeriod: String
    ): LiveData<Resource<CryptocurrencyEntity>> {
        return repo.getCryptocurrency(id, timePeriod)
    }
}