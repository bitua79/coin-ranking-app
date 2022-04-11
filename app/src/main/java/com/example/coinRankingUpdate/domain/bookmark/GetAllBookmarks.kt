package com.example.coinRankingUpdate.domain.bookmark

import com.example.coinRankingUpdate.data.repository.CryptocurrencyRepository
import javax.inject.Inject

class GetAllBookmarks @Inject constructor(
    private val repo: CryptocurrencyRepository
) {
    operator fun invoke() = repo.getBookmarkList()
}