package com.example.coinRankingUpdate.domain

import androidx.lifecycle.LiveData
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.Cryptocurrency
import com.example.coinRankingUpdate.data.repository.CryptocurrencyRepository
import javax.inject.Inject

class GetCryptocurrency @Inject constructor(
    private val repo: CryptocurrencyRepository
) {
    operator fun invoke(id: String
    ,        timePeriod: String
    ): LiveData<Resource<Cryptocurrency>> {
        return repo.getCryptocurrency(id,timePeriod)
    }
}