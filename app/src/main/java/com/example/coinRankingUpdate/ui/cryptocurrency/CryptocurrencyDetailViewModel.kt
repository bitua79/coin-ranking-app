package com.example.coinRankingUpdate.ui.cryptocurrency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.coinRankingUpdate.core.BaseViewModel
import com.example.coinRankingUpdate.core.entity.Resource
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity
import com.example.coinRankingUpdate.domain.bookmark.GetAllBookmarks
import com.example.coinRankingUpdate.domain.cryptocurrency.GetCryptocurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptocurrencyDetailViewModel @Inject constructor(
    private val getCryptocurrency: GetCryptocurrency,
    private val getAllBookmarks: GetAllBookmarks
) : BaseViewModel() {

    private var id: String = ""
    private val timePeriod: MutableLiveData<String> = MutableLiveData()
    private var ids = listOf<String>()

    fun setId(id: String) {
        this.id = id
    }

    fun setTimePeriod(timePeriod: String) {
        this.timePeriod.value = timePeriod
        refresh()
    }

    val cryptocurrencyEntityResource: LiveData<Resource<CryptocurrencyEntity>> =
        refreshing.switchMap {
            MediatorLiveData<Resource<CryptocurrencyEntity>>().apply {
                addSource(getAllBookmarks()) { newData ->
                    ids = newData.map {
                        it.bookmarkEntity.uuid
                    }
                    val data = value?.data

                    if (data == null) {
                        Resource.Success(listOf<CryptocurrencyEntity>())
                    } else
                        postValue(
                            Resource.Success(
                                data.copy(isBookmarked = newData.any { it.bookmarkEntity.uuid == data.uuid })
                            )
                        )
                }
                addSource(
                    getCryptocurrency(id, timePeriod.value ?: "24h")
                ) { newData ->
                    if (newData.data == null) {
                        Resource.Success(listOf<CryptocurrencyEntity>())
                    } else
                        postValue(
                            Resource.Success(
                                newData.data.copy(isBookmarked = ids.any { it == newData.data.uuid })
                            )

                        )
                }
            }
        }
}