package com.example.coinRankingUpdate.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.core.utils.EmptyResult
import com.example.coinRankingUpdate.core.utils.ErrorResult
import com.example.coinRankingUpdate.core.utils.SuccessResult
import com.example.coinRankingUpdate.core.utils.safeCall
import com.example.coinRankingUpdate.data.model.Cryptocurrency
import com.example.coinRankingUpdate.data.remote.WebService
import javax.inject.Inject

class DefaultCryptocurrencyRepository @Inject constructor(
    private val service: WebService
) : CryptocurrencyRepository {

    override suspend fun getAllCryptocurrencies(): LiveData<Resource<List<Cryptocurrency>>> {

        when (val response = safeCall { service.getAllCryptocurrencies() }) {
            is SuccessResult -> {
                return liveData {
                    emit(Resource.Success(response.body.data?.cryptocurrencies.orEmpty()))
                }
            }
            is ErrorResult -> {
                return liveData {
                    emit(Resource.Error(response.errorMessage, emptyList()))
                }
            }

            is EmptyResult -> {
                return liveData {
                    emit(Resource.Success(emptyList()))
                }

            }
        }
    }
}