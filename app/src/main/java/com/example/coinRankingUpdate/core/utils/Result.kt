package com.example.coinRankingUpdate.core.utils
import retrofit2.Response

@Suppress("unused") // T is used in extending classes
sealed class Result<T> {
    companion object {
        fun <T> create(error: Throwable): ErrorResult<T> {
            return ErrorResult(error.message ?: "unknown error")
        }

        fun <T> create(response: Response<T>): Result<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    EmptyResult()
                } else {
                    SuccessResult(
                        body = body
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ErrorResult(errorMsg ?: "unknown error")
            }
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class EmptyResult<T> : Result<T>()

data class SuccessResult<T>(
    val body: T
) : Result<T>()

data class ErrorResult<T>(val errorMessage: String) : Result<T>()