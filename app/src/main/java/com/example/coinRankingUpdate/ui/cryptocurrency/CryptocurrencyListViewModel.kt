package com.example.coinRankingUpdate.ui.cryptocurrency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.coinRankingUpdate.core.BaseViewModel
import com.example.coinRankingUpdate.core.entity.OrderBy
import com.example.coinRankingUpdate.core.entity.OrderDirection
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.Cryptocurrency
import com.example.coinRankingUpdate.domain.GetAllCryptocurrencies
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptocurrencyListViewModel @Inject constructor(
    private val getAllCryptocurrencies: GetAllCryptocurrencies
) : BaseViewModel() {

    private val timePeriod: MutableLiveData<String> = MutableLiveData()
    private val orderBy: MutableLiveData<OrderBy> =
        MutableLiveData(OrderBy.Price)
    private val orderDirection: MutableLiveData<OrderDirection> =
        MutableLiveData(OrderDirection.DESC)

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

    val cryptocurrenciesResource: LiveData<Resource<List<Cryptocurrency>>> = refreshing.switchMap {
        liveData {
            emitSource(
                getAllCryptocurrencies(
                    timePeriod = timePeriod.value ?: "3h",
                    orderBy = orderBy.value ?: OrderBy.Price,
                    orderDirection = orderDirection.value ?: OrderDirection.DESC
                )
            )
        }
    }

}