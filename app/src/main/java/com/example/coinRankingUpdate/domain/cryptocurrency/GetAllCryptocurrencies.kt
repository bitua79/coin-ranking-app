package com.example.coinRankingUpdate.domain.cryptocurrency

import androidx.lifecycle.LiveData
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity
import com.example.coinRankingUpdate.data.repository.CryptocurrencyRepository
import com.example.coinRankingUpdate.core.entity.OrderBy
import com.example.coinRankingUpdate.core.entity.OrderDirection
import javax.inject.Inject

class GetAllCryptocurrencies @Inject constructor(
    private val repo: CryptocurrencyRepository
) {
    operator fun invoke(
        timePeriod: String,
        orderBy: OrderBy,
        orderDirection: OrderDirection
    ): LiveData<Resource<List<CryptocurrencyEntity>>> {
        return repo.getAllCryptocurrencies(
            timePeriod,
            orderBy,
            orderDirection
        )
    }
}