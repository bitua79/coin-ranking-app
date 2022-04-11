package com.example.coinRankingUpdate.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinRankingUpdate.data.entity.BookmarkEntity
import com.example.coinRankingUpdate.data.relation.BookmarkedCrypto

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmarkEntityId: BookmarkEntity)

    @Query("DELETE FROM tbl_bookmark WHERE cryptoId= :id")
    suspend fun deleteBookmark(id: String)

    @Query("SELECT * FROM tbl_bookmark")
    fun getAllBookmark(): LiveData<List<BookmarkedCrypto>>
}