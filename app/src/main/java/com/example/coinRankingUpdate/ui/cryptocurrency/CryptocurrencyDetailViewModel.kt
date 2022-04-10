package com.example.coinRankingUpdate.ui.cryptocurrency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.coinRankingUpdate.core.BaseViewModel
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.Cryptocurrency
import com.example.coinRankingUpdate.domain.GetCryptocurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptocurrencyDetailViewModel @Inject constructor(
    private val getCryptocurrency: GetCryptocurrency
) : BaseViewModel() {

    private var id: String = ""
    private val timePeriod: MutableLiveData<String> = MutableLiveData()

    fun setId(id: String) {
        this.id = id
    }

    fun setTimePeriod(timePeriod: String) {
        this.timePeriod.value = timePeriod
        refresh()
    }

    val cryptocurrencyResource: LiveData<Resource<Cryptocurrency>> = refreshing.switchMap {
        liveData {
            emitSource(getCryptocurrency(id, timePeriod.value ?: "24h"))
        }
    }
}