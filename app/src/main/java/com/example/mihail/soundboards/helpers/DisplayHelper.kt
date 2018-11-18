package com.example.mihail.soundboards.helpers

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.example.mihail.soundboards.App

class DisplayHelper {
    companion object {

        val widthPixels: Int
            get() {
                val displaymetrics = App.instance.resources.displayMetrics
                val metrics = DisplayMetrics()
                val windowManager =
                    App.instance.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                windowManager.defaultDisplay.getMetrics(metrics)
                val display = windowManager.defaultDisplay
                val metricsB = DisplayMetrics()
                display.getMetrics(metricsB)
                return displaymetrics.widthPixels
            }

        val heightPixels: Int
            get() {
                val displaymetrics = App.instance.resources.displayMetrics
                val metrics = DisplayMetrics()
                val windowManager =
                    App.instance.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                windowManager.defaultDisplay.getMetrics(metrics)
                val display = windowManager.defaultDisplay
                val metricsB = DisplayMetrics()
                display.getMetrics(metricsB)
                return displaymetrics.heightPixels
            }

        fun dpToPx(dp: Int): Int {
            val displayMetrics = App.instance.resources.displayMetrics
            return Math.round(dp * displayMetrics.density)
        }

        fun pxToDp(px: Int): Int {
            val displayMetrics = App.instance.resources.displayMetrics
            return Math.round(px / displayMetrics.density)
        }


    }
}