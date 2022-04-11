package com.example.coinRankingUpdate.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "tbl_bookmark"
)
class BookmarkEntity(
    @ColumnInfo(name = "cryptoId")
    @PrimaryKey
    val uuid: String
)