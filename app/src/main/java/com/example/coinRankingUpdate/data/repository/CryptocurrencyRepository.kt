package com.example.coinRankingUpdate.data.repository

import androidx.lifecycle.LiveData
import com.example.coinRankingUpdate.core.entity.OrderBy
import com.example.coinRankingUpdate.core.entity.OrderDirection
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.BookmarkEntity
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity
import com.example.coinRankingUpdate.data.relation.BookmarkedCrypto

interface CryptocurrencyRepository {
    fun getAllCryptocurrencies(
        timePeriod: String,
        orderBy: OrderBy,
        orderDirection: OrderDirection
    ): LiveData<Resource<List<CryptocurrencyEntity>>>

    fun getCryptocurrency(
        id: String,
        timePeriod: String
    ): LiveData<Resource<CryptocurrencyEntity>>

    suspend fun getCryptocurrenciesByQuery(
        query: String
    ): LiveData<Resource<List<CryptocurrencyEntity>>>

    fun getBookmarkList():  LiveData<List<BookmarkedCrypto>>

    suspend fun doBookmark(bookmarkEntity: BookmarkEntity)

    suspend fun doUnBookmark(id: String)
}