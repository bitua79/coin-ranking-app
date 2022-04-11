package com.example.coinRankingUpdate.ui.bookmark

import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.coinRankingUpdate.core.BaseViewModel
import com.example.coinRankingUpdate.data.entity.BookmarkEntity
import com.example.coinRankingUpdate.domain.bookmark.DoBookmark
import com.example.coinRankingUpdate.domain.bookmark.DoUnBookmark
import com.example.coinRankingUpdate.domain.bookmark.GetAllBookmarks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getAllBookmarks: GetAllBookmarks,
    private val doBookmark: DoBookmark,
    private val doUnBookmark: DoUnBookmark
) : BaseViewModel() {

    val bookmarkedCryptocurrencies = liveData(Dispatchers.IO) {
        emitSource(
            getAllBookmarks().map {
                it
            }
        )
    }

    fun bookmark(entity: BookmarkEntity) {
        viewModelScope.launch {
            doBookmark(entity)
        }
    }

    fun unBookmark(id: String) {
        viewModelScope.launch {
            doUnBookmark(id)
        }
    }
}