package com.example.coinRankingUpdate.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.coinRankingUpdate.core.NetworkBoundResource
import com.example.coinRankingUpdate.core.entity.APIResponse
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.core.utils.EmptyResult
import com.example.coinRankingUpdate.core.utils.ErrorResult
import com.example.coinRankingUpdate.core.utils.SuccessResult
import com.example.coinRankingUpdate.core.utils.safeCall
import com.example.coinRankingUpdate.data.entity.Cryptocurrencies
import com.example.coinRankingUpdate.data.entity.Cryptocurrency
import com.example.coinRankingUpdate.data.local.CryptocurrenciesDao
import com.example.coinRankingUpdate.data.remote.WebService
import retrofit2.Response
import javax.inject.Inject

class DefaultCryptocurrencyRepository @Inject constructor(
    private val service: WebService,
    private val dao: CryptocurrenciesDao
) : CryptocurrencyRepository {

    override fun getAllCryptocurrencies(): LiveData<Resource<List<Cryptocurrency>>> =
        object : NetworkBoundResource<List<Cryptocurrency>, APIResponse<Cryptocurrencies>>() {
            override suspend fun saveCallResult(response: APIResponse<Cryptocurrencies>) {
                dao.insertAllCryptocurrencies(response.data?.cryptocurrencies.orEmpty())
            }

            override fun loadFromDb(): LiveData<List<Cryptocurrency>> {
                return dao.getAllCryptocurrencies()
            }

            override suspend fun createCall(): Response<APIResponse<Cryptocurrencies>> {
                return service.getAllCryptocurrencies()
            }

        }.asLiveData()

//    override suspend fun getAllCryptocurrencies(): LiveData<Resource<List<Cryptocurrency>>> {
//
//        when (val response = safeCall { service.getAllCryptocurrencies() }) {
//            is SuccessResult -> {
//                return liveData {
//                    emit(Resource.Success(response.body.data?.cryptocurrencies.orEmpty()))
//                }
//            }
//            is ErrorResult -> {
//                return liveData {
//                    emit(Resource.Error(response.errorMessage, emptyList()))
//                }
//            }
//
//            is EmptyResult -> {
//                return liveData {
//                    emit(Resource.Success(emptyList()))
//                }
//
//            }
//        }
//    }
}