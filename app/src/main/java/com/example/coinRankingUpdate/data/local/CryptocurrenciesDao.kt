package com.example.coinRankingUpdate.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity

@Dao
interface CryptocurrenciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCryptocurrencies(cryptocurrencyEntities: List<CryptocurrencyEntity>)

    @RawQuery(observedEntities = [CryptocurrencyEntity::class])
    fun getAllCryptocurrencies(query: SupportSQLiteQuery): LiveData<List<CryptocurrencyEntity>>

    @Query("SELECT * FROM tbl_cryptocurrency WHERE id=:id")
    fun getCryptocurrencyById(id: String): LiveData<CryptocurrencyEntity>

    @Update
    fun update(cryptocurrencyEntity: CryptocurrencyEntity)

    @Query("UPDATE tbl_cryptocurrency SET isBookmarked=1 WHERE id=:id")
    suspend fun bookmarkCryptocurrency(id: String)

    @Query("UPDATE tbl_cryptocurrency SET isBookmarked=0 WHERE id=:id")
    suspend fun unBookmarkCryptocurrency(id: String)
}