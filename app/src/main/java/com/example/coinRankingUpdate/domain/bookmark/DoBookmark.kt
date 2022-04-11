package com.example.coinRankingUpdate.domain.bookmark

import com.example.coinRankingUpdate.data.entity.BookmarkEntity
import com.example.coinRankingUpdate.data.repository.CryptocurrencyRepository
import javax.inject.Inject

class DoBookmark @Inject constructor(
    private val repo: CryptocurrencyRepository
) {
    suspend operator fun invoke(entity: BookmarkEntity) = repo.doBookmark(entity)
}