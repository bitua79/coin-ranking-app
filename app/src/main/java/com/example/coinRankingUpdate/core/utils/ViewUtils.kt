package com.example.coinRankingUpdate.ui

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.ArrayRes
import com.example.coinRankingUpdate.R

fun setSpinner(
    context: Context,
    spinner: android.widget.Spinner,
    @ArrayRes res: Int,
    onItemSelected: (String) -> Unit
) {
    val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
        context,
        res,
        R.layout.spinner_header
    )
    adapter.setDropDownViewResource(R.layout.spinner_list_item)
    spinner.adapter = adapter
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>,
            view: View?,
            pos: Int,
            id: Long
        ) {
            onItemSelected(parent.getItemAtPosition(pos).toString())
        }

        override fun onNothingSelected(parent: AdapterView<*>) {}
    }
}

fun View.gone(){
    visibility = View.GONE
}

fun View.visible(){
    visibility = View.VISIBLE
}