package com.example.coinRankingUpdate.data.model

data class Cryptocurrency(
    val name: String,
    val symbol: String,
    val description: String?,
    val iconUrl: String,
    val marketCap: String?,
    val price: String,
    val change: String?,
    val rank: Int,
    val Volume24H: String,
    val btcPrice: String,
    val allTimeHigh: AllTimeHigh?
)

data class AllTimeHigh(
    val allTimeHighPrice: String,
    val timestamp: Long
)