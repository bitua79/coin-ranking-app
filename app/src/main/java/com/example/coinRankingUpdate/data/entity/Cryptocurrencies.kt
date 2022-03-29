package com.example.coinRankingUpdate.data.entity

import com.google.gson.annotations.SerializedName

data class Cryptocurrencies(
    @SerializedName("coins")
    val cryptocurrencies: List<Cryptocurrency>? = null
)