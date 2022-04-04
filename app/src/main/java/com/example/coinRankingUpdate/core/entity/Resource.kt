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
        return handle(tag, context, errMsg)
    }

    fun handle(
        tag: String,
        context: Context,
        errMsg: String,
        startLoad: () -> Unit,
        endLoad: () -> Unit
    ): T? {
        return when (this) {
            is Error -> {
                endLoad()
                Log.e(tag, "Failed to load resources from server.")
                Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show()
                null
            }
            is Success -> {
                endLoad()
                Log.e(tag, "Resources loaded successfully.")
                this.data
            }
            is Loading -> {
                startLoad()
                Log.e(tag, "Loading resources ...")
                Toast.makeText(context, "Please wait ...", Toast.LENGTH_SHORT).show()
                this.data
            }
        }
    }
}