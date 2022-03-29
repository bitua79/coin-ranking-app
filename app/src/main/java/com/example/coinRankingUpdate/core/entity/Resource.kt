package com.example.coinRankingUpdate.core.entity

import android.content.Context
import android.util.Log
import android.widget.Toast

sealed class Resource<T>(
    val data: T? = null,
    val status: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    fun handle(tag: String, context: Context, errMsg: String): T? {
        return when (this) {
            is Error -> {
                Log.e(tag, "Failed to load resources from server.")
                Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show()
                null
            }
            is Success -> {
                Log.e(tag, "Resources loaded successfully.")
                this.data
            }
            is Loading -> {
                Log.e(tag, "Loading resources ...")
                this.data
            }
        }
    }

}