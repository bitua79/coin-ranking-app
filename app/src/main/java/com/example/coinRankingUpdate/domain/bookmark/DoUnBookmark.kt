package com.example.coinRankingUpdate.domain.bookmark

import com.example.coinRankingUpdate.data.repository.CryptocurrencyRepository
import javax.inject.Inject

class DoUnBookmark @Inject constructor(
    private val repo: CryptocurrencyRepository
) {
    suspend operator fun invoke(id: String) = repo.doUnBookmark(id)
}