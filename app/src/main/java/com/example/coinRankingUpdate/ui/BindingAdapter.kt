package com.example.coinRankingUpdate.ui

import android.net.Uri
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.coinRankingUpdate.R
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.material.imageview.ShapeableImageView
import java.text.DecimalFormat

@BindingAdapter("formatPrice")
fun formatPrice(view: TextView, price: String?) {
    val num = stringToDouble(price)
    val decimalFormat = DecimalFormat("#,##0.00")
    view.text = decimalFormat.format(num).toString()
}

@BindingAdapter("setChange")
fun setChange(view: TextView, change: String?) {
    val decimalFormat = DecimalFormat("#,##0.00")
    val num = stringToDouble(change)
    var text = "${decimalFormat.format(num)}%"
    val color = if (num < 0)
        R.color.color_decrease
    else {
        text = "+$text"
        R.color.color_increase
    }
    view.setTextColor(ContextCompat.getColor(view.context, color))
    view.text = text
}

fun stringToDouble(txt: String?): Double {
    return txt?.toDoubleOrNull() ?: 0.0
}

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ShapeableImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        if (imageUrl.endsWith("svg")) {
            GlideToVectorYou
                .init()
                .with(view.context)
                .load(Uri.parse(imageUrl), view)
        } else {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }
}

