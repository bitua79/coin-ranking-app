package com.example.coinRankingUpdate.domain

import androidx.lifecycle.LiveData
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.Cryptocurrency
import com.example.coinRankingUpdate.data.repository.CryptocurrencyRepository
import com.example.coinRankingUpdate.ui.cryptocurrency.OrderBy
import com.example.coinRankingUpdate.ui.cryptocurrency.OrderDirection
import javax.inject.Inject

class GetAllCryptocurrencies @Inject constructor(
    private val repo: CryptocurrencyRepository
) {
    operator fun invoke(
        timePeriod: String,
        orderBy: OrderBy,
        orderDirection: OrderDirection
    ): LiveData<Resource<List<Cryptocurrency>>> {
        return repo.getAllCryptocurrencies(
            timePeriod,
            orderBy,
            orderDirection
        )
    }
}