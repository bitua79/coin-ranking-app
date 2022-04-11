package com.example.coinRankingUpdate.data.entity

import com.google.gson.annotations.SerializedName

data class CryptocurrenciesResponse(
    @SerializedName("coins")
    val cryptocurrencyEntities: List<CryptocurrencyEntity>? = null
)