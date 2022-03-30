package com.example.coinRankingUpdate.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.coinRankingUpdate.R
import com.example.coinRankingUpdate.data.entity.Cryptocurrency
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.material.imageview.ShapeableImageView
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

@BindingAdapter("formatPrice")
fun formatPrice(view: TextView, price: String?) {
    val num = price.toDouble()
    val decimalFormat = DecimalFormat("#,##0.00")
    view.text = decimalFormat.format(num).toString()
}

@BindingAdapter("setChange")
fun setChange(view: TextView, change: String?) {
    val decimalFormat = DecimalFormat("#,##0.00")
    val num = change.toDouble()
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

@BindingAdapter("formatPriceBTC")
fun formatPriceBTC(view: TextView, price: String?) {
    val num = price.toDouble()
    val decimalFormat = DecimalFormat("#,##0.00")
    view.text = view.context.getString(R.string.format_btc, decimalFormat.format(num))
}

@BindingAdapter("inUnit")
fun inUnit(view: TextView, price: String?) {
    val decimalFormat = DecimalFormat("#,##0.00")

    val num = price.toDouble()
    with(view.context) {
        view.text = when {
            num > 1000000000 -> {
                getString(R.string.format_billion, decimalFormat.format(num / 1000000000))
            }
            num > 1000000 -> {
                getString(R.string.format_million, decimalFormat.format(num / 1000000))
            }
            num > 1000 -> {
                getString(R.string.format_k, decimalFormat.format(num / 1000))
            }
            else -> num.toString()
        }
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter(value = ["priceChangeCrypto", "priceChangeIsBtc"], requireAll = true)
fun priceChange(view: TextView, crypto: Cryptocurrency, isBtc: Boolean) {
    with(crypto) {
        val price = (if (isBtc) crypto.btcPrice else crypto.price).toDouble()
        val change = crypto.change.toDouble()
        var pChange = price * change / (change + 1.0)
        pChange = abs(pChange)
        val decimalFormat = DecimalFormat("#,##0.00")
        view.text = if (isBtc) {
            "${decimalFormat.format(pChange)} BTC"
        } else {
            "$ ${decimalFormat.format(pChange)}"
        }
    }
}

@SuppressLint("SimpleDateFormat", "SetTextI18n")
@BindingAdapter("timeConverter")
fun timeConverter(view: TextView, timeStamp: Long) {
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    val netDate = Date(timeStamp * 1000)
    val date = sdf.format(netDate)
    view.text = "on $date"
}

@BindingAdapter("renderHtml")
fun bindRenderHtml(view: TextView, description: String?) {
    if (description != null) {
        view.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}

fun String?.toDouble(): Double {
    return this?.toDoubleOrNull() ?: 0.0
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