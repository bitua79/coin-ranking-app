package com.example.coinRankingUpdate.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.coinRankingUpdate.core.BaseViewModel
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.Cryptocurrency
import com.example.coinRankingUpdate.domain.GetCryptocurrencyByQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCryptocurrencies: GetCryptocurrencyByQuery,
) : BaseViewModel() {

    private val query: MutableLiveData<String> = MutableLiveData()

    // Request to server by changing query
    fun search(entry: String) {
        query.postValue(entry)
        this.refresh()
    }

    // Request to server to get coins by query
    val cryptocurrenciesResource: LiveData<Resource<List<Cryptocurrency>>> = refreshing.switchMap {
        liveData(Dispatchers.IO) {
            delay(1000)
            emitSource(getCryptocurrencies(query.value.orEmpty()))
        }
    }
}
