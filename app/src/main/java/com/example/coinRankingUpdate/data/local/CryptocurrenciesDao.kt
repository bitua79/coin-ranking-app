package com.example.coinRankingUpdate.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.coinRankingUpdate.data.entity.Cryptocurrency

@Dao
interface CryptocurrenciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCryptocurrencies(cryptocurrencies: List<Cryptocurrency>)

    @RawQuery(observedEntities = [Cryptocurrency::class])
    fun getAllCryptocurrencies(query: SupportSQLiteQuery): LiveData<List<Cryptocurrency>>

    @Query("SELECT * FROM tbl_cryptocurrency WHERE id=:id")
    fun getCryptocurrencyById(id: String): LiveData<Cryptocurrency>

    @Update
    fun update(cryptocurrency: Cryptocurrency)

}