package com.example.coinRankingUpdate.core

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

sealed class AdapterDataChanges {

    data class RangeInserted(val positionStart: Int, val itemCount: Int) : AdapterDataChanges()

    data class RangeChanged(
        val positionStart: Int,
        val itemCount: Int,
        val payload: Any?
    ) : AdapterDataChanges()

    data class RangeMoved(
        val fromPosition: Int,
        val toPosition: Int,
        val itemCount: Int
    ) : AdapterDataChanges()

    data class RangeRemoved(val positionStart: Int, val itemCount: Int) : AdapterDataChanges()
}

fun initRecyclerViewAdapterDataObserver(
    callback: (change: AdapterDataChanges) -> Unit
): RecyclerView.AdapterDataObserver {

    return object : RecyclerView.AdapterDataObserver() {

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            try {
                callback(AdapterDataChanges.RangeInserted(positionStart, itemCount))
            } catch (e: IllegalStateException) {
                throw e
            }
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            try {
                callback(AdapterDataChanges.RangeChanged(positionStart, itemCount, payload))
            } catch (e: IllegalStateException) {
                throw e
            }
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            try {
                callback(AdapterDataChanges.RangeMoved(fromPosition, toPosition, itemCount))
            } catch (e: IllegalStateException) {
                throw e
            }
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            try {
                callback(AdapterDataChanges.RangeRemoved(positionStart, itemCount))
            } catch (e: IllegalStateException) {
                throw e
            }
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            try {
                callback(AdapterDataChanges.RangeChanged(positionStart, itemCount, null))
            } catch (e: IllegalStateException) {
                throw e
            }
        }
    }
}

/**
 * find the first visible Item's position if layoutManager is [LinearLayoutManager]
 *
 * @return -1 if layoutManager is null or it is not LinearLayoutManager, and if it is
 * LinearLayoutManager then response of [LinearLayoutManager.findFirstVisibleItemPosition]
 * */
fun RecyclerView.LayoutManager?.findFirstVisiblePosition(): Int {
    return if (this == null || this !is LinearLayoutManager)
        -1
    else
        this.findFirstVisibleItemPosition()
}

/**
 * find the last visible Item position if layoutManager is [LinearLayoutManager]
 *
 * @return -1 if layoutManager is null or it is not LinearLayoutManager, and if it is
 * LinearLayoutManager then response of [LinearLayoutManager.findLastVisibleItemPosition]
 * */
fun RecyclerView.LayoutManager?.findLastVisiblePosition(): Int {
    return if (this == null || this !is LinearLayoutManager)
        -1
    else
        this.findLastVisibleItemPosition()
}

/**
 *  check if an item is inserted before the first visible item in recyclerView while in first page
 *  @param change represents a change in RecyclerView Adapter which it can be insert, move,
 *  remove and change
 *  @return true if we are in first page and an item is inserted before first item, otherwise false
 *
 * */
fun isMoved(
    layoutManager: RecyclerView.LayoutManager?,
    change: AdapterDataChanges,
): Boolean {
    // we want to scroll up by ever change
    if (layoutManager == null) return false

    if (change is AdapterDataChanges.RangeMoved) {
        return true
    }
    return false
}

fun scrollUpByChange(): Boolean {
    return true
}

// TODO: Add documentation
fun <VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.tryToRegisterAdapterDataObserver(
    observer: RecyclerView.AdapterDataObserver?
): Boolean {
    if (observer == null) return false
    return try {
        this.registerAdapterDataObserver(observer)
        true
    } catch (t: Throwable) {
        false
    }
}

fun <VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.tryToUnregisterAdapterDataObserver(
    observer: RecyclerView.AdapterDataObserver?
): Boolean {
    if (observer == null) return false
    return try {
        this.unregisterAdapterDataObserver(observer)
        true
    } catch (t: Throwable) {
        false
    }
}