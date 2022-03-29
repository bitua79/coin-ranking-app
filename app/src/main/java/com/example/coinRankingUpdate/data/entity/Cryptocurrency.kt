package com.example.coinRankingUpdate.data.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "tbl_cryptocurrency"
)
data class Cryptocurrency(
    @ColumnInfo(name = "id")
    @PrimaryKey
    val uuid: String,
    val name: String,
    val symbol: String,
    val description: String?,
    val iconUrl: String,
    val marketCap: String?,
    val price: String,
    val change: String?,
    val rank: Int,
    @field:SerializedName("24hVolume")
    val Volume24H: String,
    val btcPrice: String,
    @Embedded
    val allTimeHigh: AllTimeHigh?
)

data class AllTimeHigh(
    @field:SerializedName("price")
    val allTimeHighPrice: String,
    val timestamp: Long
)