package com.example.coinRankingUpdate.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.coinRankingUpdate.core.NetworkBoundResource
import com.example.coinRankingUpdate.core.entity.APIResponse
import com.example.coinRankingUpdate.core.entity.OrderBy
import com.example.coinRankingUpdate.core.entity.OrderDirection
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.core.utils.EmptyResult
import com.example.coinRankingUpdate.core.utils.ErrorResult
import com.example.coinRankingUpdate.core.utils.SuccessResult
import com.example.coinRankingUpdate.core.utils.safeCall
import com.example.coinRankingUpdate.data.entity.BookmarkEntity
import com.example.coinRankingUpdate.data.entity.CryptocurrenciesResponse
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity
import com.example.coinRankingUpdate.data.entity.CryptocurrencyResponse
import com.example.coinRankingUpdate.data.local.BookmarkDao
import com.example.coinRankingUpdate.data.local.CryptocurrenciesDao
import com.example.coinRankingUpdate.data.relation.BookmarkedCrypto
import com.example.coinRankingUpdate.data.remote.WebService
import retrofit2.Response
import javax.inject.Inject



class DefaultCryptocurrencyRepository @Inject constructor(
    private val service: WebService,
    private val cryptocurrencyDao: CryptocurrenciesDao,
    private val bookmarkDao: BookmarkDao,
) : CryptocurrencyRepository {

    override fun getAllCryptocurrencies(
        timePeriod: String,
        orderBy: OrderBy,
        orderDirection: OrderDirection
    ): LiveData<Resource<List<CryptocurrencyEntity>>> =
        object :
            NetworkBoundResource<List<CryptocurrencyEntity>, APIResponse<CryptocurrenciesResponse>>() {
            override suspend fun saveCallResult(response: APIResponse<CryptocurrenciesResponse>) {
                cryptocurrencyDao.insertAllCryptocurrencies(response.data?.cryptocurrencyEntities.orEmpty())
            }

            override fun loadFromDb(): LiveData<List<CryptocurrencyEntity>> {
                val query = SimpleSQLiteQuery(
                    "SELECT * FROM tbl_cryptocurrency ORDER BY CAST(${orderBy.value} AS REAL) ${orderDirection.value} "
                )
                return cryptocurrencyDao.getAllCryptocurrencies(query)
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
    ): LiveData<Resource<CryptocurrencyEntity>> =
        object :
            NetworkBoundResource<CryptocurrencyEntity, APIResponse<CryptocurrencyResponse>>() {
            override suspend fun saveCallResult(response: APIResponse<CryptocurrencyResponse>) {
                response.data?.cryptocurrencyEntity?.let {
                    cryptocurrencyDao.update(it)
                }
            }

            override fun loadFromDb(): LiveData<CryptocurrencyEntity> {
                return cryptocurrencyDao.getCryptocurrencyById(id)
            }

            override suspend fun createCall(): Response<APIResponse<CryptocurrencyResponse>> {
                return service.getCryptocurrency(id, timePeriod)
            }

        }.asLiveData()

    override suspend fun getCryptocurrenciesByQuery(query: String): LiveData<Resource<List<CryptocurrencyEntity>>> {
        when (val response = safeCall { service.getCryptocurrenciesByQuery(query) }) {
            is SuccessResult -> {
                return liveData {
                    emit(Resource.Success(response.body.data?.cryptocurrencyEntities.orEmpty()))
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

    override fun getBookmarkList(): LiveData<List<BookmarkedCrypto>> =
        bookmarkDao.getAllBookmark()


    override suspend fun doBookmark(bookmarkEntity: BookmarkEntity) {
        bookmarkDao.insertBookmark(bookmarkEntity)
        cryptocurrencyDao.bookmarkCryptocurrency(bookmarkEntity.uuid)

    }

    override suspend fun doUnBookmark(id: String) {
        bookmarkDao.deleteBookmark(id)
        cryptocurrencyDao.unBookmarkCryptocurrency(id)
    }

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
