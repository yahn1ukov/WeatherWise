package com.ua.weatherwise.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.ua.weatherwise.R
import com.ua.weatherwise.utils.Constants.Companion.ICON_URL

class BindingAdapter {
    companion object {
        @BindingAdapter("android:imageLoad")
        @JvmStatic
        fun imageLoad(imageView: ImageView, iconId: String) {
            imageView.load("$ICON_URL/$iconId.png") {
                error(R.drawable.ic_no_image)
            }
        }

        @BindingAdapter("android:showImageTextError")
        @JvmStatic
        fun showImageTextError(view: View, error: NetworkResult<*>?) {
            if (error is NetworkResult.Error) {
                when (view) {
                    is ImageView -> view.isVisible = true
                    is TextView -> {
                        view.isVisible = true
                        view.text = error.message.toString()
                    }
                }
            }
        }

        @BindingAdapter("android:showTemperatureScale")
        @JvmStatic
        fun showTemperatureScale(view: TextView, units: String) {
            if (units == "Metric") {
                view.text = "°C"
            } else {
                view.text = "°F"
            }
        }
    }
}