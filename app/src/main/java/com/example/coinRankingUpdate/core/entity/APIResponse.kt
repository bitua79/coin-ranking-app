package com.example.coinRankingUpdate.core.entity

data class APIResponse<T>(
    val status: String? = null,
    val data: T? = null,
)