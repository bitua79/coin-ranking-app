package com.example.coinRankingUpdate.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.coinRankingUpdate.data.entity.BookmarkEntity
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity


data class BookmarkedCrypto(
    @Embedded val bookmarkEntity: BookmarkEntity,
    @Relation(
        parentColumn = "cryptoId",
        entityColumn = "id"
    )
    val cryptocurrencyEntity: CryptocurrencyEntity?
)