package com.ua.weatherwise.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.ua.weatherwise.R
import com.ua.weatherwise.utils.Constants.Companion.ICON_URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object WeatherAdapter {
    @BindingAdapter("android:loadImage")
    @JvmStatic
    fun loadImage(imageView: ImageView, iconId: String?) {
        imageView.load("$ICON_URL/$iconId@2x.png") {
            error(R.drawable.ic_no_image)
        }
    }

    @BindingAdapter("android:convertToTemperature")
    @JvmStatic
    fun convertToTemperature(textView: TextView, number: Double) {
        textView.text = "${number.toInt()}°"
    }

    @BindingAdapter("android:convertToPercentages")
    @JvmStatic
    fun convertToPercentages(textView: TextView, number: Double) {
        textView.text = "${number.toInt()}%"
    }

    @BindingAdapter("android:convertToPercentages")
    @JvmStatic
    fun convertToPercentages(textView: TextView, number: Int) {
        textView.text = "${number}%"
    }

    @BindingAdapter("android:convertToSpeed")
    @JvmStatic
    fun convertToSpeed(textView: TextView, number: Double) {
        textView.text = "${number.toInt()}km/h"
    }

    @BindingAdapter("android:convertToDate")
    @JvmStatic
    fun convertToDate(textView: TextView, number: Int) {
        val date = Date(number.toLong())
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        textView.text = format.format(date)
    }
}