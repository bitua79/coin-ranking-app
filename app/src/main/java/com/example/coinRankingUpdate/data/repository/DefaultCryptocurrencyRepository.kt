package com.example.coinRankingUpdate.data.repository

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.coinRankingUpdate.core.NetworkBoundResource
import com.example.coinRankingUpdate.core.entity.APIResponse
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.CryptocurrenciesResponse
import com.example.coinRankingUpdate.data.entity.Cryptocurrency
import com.example.coinRankingUpdate.data.entity.CryptocurrencyResponse
import com.example.coinRankingUpdate.data.local.CryptocurrenciesDao
import com.example.coinRankingUpdate.data.remote.WebService
import com.example.coinRankingUpdate.core.entity.OrderBy
import com.example.coinRankingUpdate.core.entity.OrderDirection
import retrofit2.Response
import javax.inject.Inject

class DefaultCryptocurrencyRepository @Inject constructor(
    private val service: WebService,
    private val dao: CryptocurrenciesDao
) : CryptocurrencyRepository {

    override fun getAllCryptocurrencies(
        timePeriod: String,
        orderBy: OrderBy,
        orderDirection: OrderDirection
    ): LiveData<Resource<List<Cryptocurrency>>> =
        object :
            NetworkBoundResource<List<Cryptocurrency>, APIResponse<CryptocurrenciesResponse>>() {
            override suspend fun saveCallResult(response: APIResponse<CryptocurrenciesResponse>) {
                dao.insertAllCryptocurrencies(response.data?.cryptocurrencies.orEmpty())
            }

            override fun loadFromDb(): LiveData<List<Cryptocurrency>> {
                val query = SimpleSQLiteQuery(
                    "SELECT * FROM tbl_cryptocurrency ORDER BY CAST(${orderBy.value} AS REAL) ${orderDirection.value} "
                )
                return dao.getAllCryptocurrencies(query)
            }

            override suspend fun createCall(): Response<APIResponse<CryptocurrenciesResponse>> {
                return service.getAllCryptocurrencies(
                    timePeriod,
                    orderBy.value,
                    orderDirection.value
                )
            }

        }.asLiveData()


    override fun getCryptocurrency(
        id: String,
        timePeriod: String
    ): LiveData<Resource<Cryptocurrency>> =
        object :
            NetworkBoundResource<Cryptocurrency, APIResponse<CryptocurrencyResponse>>() {
            override suspend fun saveCallResult(response: APIResponse<CryptocurrencyResponse>) {
                response.data?.cryptocurrency?.let {
                    dao.update(it)
                }
            }

            override fun loadFromDb(): LiveData<Cryptocurrency> {
                return dao.getCryptocurrencyById(id)
            }

            override suspend fun createCall(): Response<APIResponse<CryptocurrencyResponse>> {
                return service.getCryptocurrency(id, timePeriod)
            }

        }.asLiveData()

    // when we don't hae database and we don't want to use network bound resource
/*    override suspend fun getAllCryptocurrencies(): LiveData<Resource<List<Cryptocurrency>>> {

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
    }*/
}