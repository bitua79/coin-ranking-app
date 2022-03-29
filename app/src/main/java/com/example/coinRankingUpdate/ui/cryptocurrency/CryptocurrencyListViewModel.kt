package com.example.coinRankingUpdate.ui.cryptocurrency

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.coinRankingUpdate.core.BaseViewModel
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.Cryptocurrency
import com.example.coinRankingUpdate.domain.GetAllCryptocurrencies
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptocurrencyListViewModel @Inject constructor(
    private val getAllCryptocurrencies: GetAllCryptocurrencies
) : BaseViewModel() {

    val cryptocurrenciesResource: LiveData<Resource<List<Cryptocurrency>>> = refreshing.switchMap {
        liveData {
            emitSource(getAllCryptocurrencies())
        }
    }

}