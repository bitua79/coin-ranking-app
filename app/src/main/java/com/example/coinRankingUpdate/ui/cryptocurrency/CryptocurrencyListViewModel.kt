package com.example.coinRankingUpdate.ui.cryptocurrency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.coinRankingUpdate.core.BaseViewModel
import com.example.coinRankingUpdate.core.entity.OrderBy
import com.example.coinRankingUpdate.core.entity.OrderDirection
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity
import com.example.coinRankingUpdate.domain.bookmark.GetAllBookmarks
import com.example.coinRankingUpdate.domain.cryptocurrency.GetAllCryptocurrencies
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptocurrencyListViewModel @Inject constructor(
    private val getAllCryptocurrencies: GetAllCryptocurrencies,
    private val getAllBookmarks: GetAllBookmarks
) : BaseViewModel() {

    private val timePeriod: MutableLiveData<String> = MutableLiveData()
    private val orderBy: MutableLiveData<OrderBy> =
        MutableLiveData(OrderBy.Price)
    private val orderDirection: MutableLiveData<OrderDirection> =
        MutableLiveData(OrderDirection.DESC)
    private var ids = listOf<String>()

    fun setPriceFilter(order: OrderDirection) {
        orderBy.value = OrderBy.Price
        orderDirection.value = order
        refresh()
    }

    fun setMarketCapFilter(order: OrderDirection) {
        orderBy.value = OrderBy.MarketCap
        orderDirection.value = order
        refresh()
    }

    fun setTimePeriod(timePeriod: String) {
        this.timePeriod.value = timePeriod
        refresh()
    }

    // Merge coin list and bookmark list to update one other when each of them is changed
    val cryptocurrenciesResource: LiveData<Resource<List<CryptocurrencyEntity>>> =
        refreshing.switchMap {
            MediatorLiveData<Resource<List<CryptocurrencyEntity>>>().apply {
                addSource(getAllBookmarks()) { newData ->
                    ids = newData.map {
                        it.bookmarkEntity.uuid
                    }
                    val data = value?.data.orEmpty()

                    postValue(
                        Resource.Success(
                            data.map { coin ->
                                coin.copy(isBookmarked = newData.any { it.bookmarkEntity.uuid == coin.uuid })
                            }
                        )
                    )
                }
                addSource(
                    getAllCryptocurrencies(
                        timePeriod = timePeriod.value ?: "3h",
                        orderBy = orderBy.value ?: OrderBy.Price,
                        orderDirection = orderDirection.value ?: OrderDirection.DESC
                    )
                ) { newData ->
                    postValue(
                        Resource.Success(
                            newData.data.orEmpty().map { crypto ->
                                crypto.copy(isBookmarked = ids.any { it == crypto.uuid })
                            }
                        )
                    )
                }
            }
        }
}