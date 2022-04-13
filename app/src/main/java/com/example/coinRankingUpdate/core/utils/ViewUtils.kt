package com.example.coinRankingUpdate.ui

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.ArrayRes
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import com.example.coinRankingUpdate.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun TextInputEditText.doAfterTextChanged(
    lifecycleOwner: LifecycleOwner,
    delayTime: Long,
    callback: () -> Unit
) {
    var typingJob: Job? = null
    val lifecycle = lifecycleOwner.lifecycle

    doAfterTextChanged {
        if (typingJob?.isActive == true) typingJob?.cancel()

        typingJob = lifecycle.coroutineScope.launch {
            delay(delayTime)

            if (lifecycle.currentState.isAtLeast((Lifecycle.State.STARTED))) {
                callback()
            }
        }
    }
}

fun Fragment.hideSystemUI() {
    val decorView = activity!!.window.decorView
    val uiOptions = decorView.systemUiVisibility
    var newUiOptions = uiOptions
    newUiOptions =
        newUiOptions or View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

    decorView.systemUiVisibility = newUiOptions
}

fun Fragment.showSystemUI() {
    val decorView = activity!!.window.decorView
    val uiOptions = decorView.systemUiVisibility
    var newUiOptions = uiOptions
    newUiOptions =
        newUiOptions and View.SYSTEM_UI_FLAG_LOW_PROFILE.inv() and
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv() and
                View.SYSTEM_UI_FLAG_IMMERSIVE.inv() and
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
    decorView.systemUiVisibility = newUiOptions
}