package com.example.mihail.soundboards

import android.app.Application
import com.example.mihail.soundboards.manager.FFmpegManager

class App : Application() {

    var width:Int = 0
    var height:Int = 0

    private object Holder {
        lateinit var instance: App
    }

    companion object {
        private const val LOG_TAG = "App"
        val instance: App by lazy { Holder.instance }
    }

    override fun onCreate() {
        super.onCreate()
        Holder.instance = this
        FFmpegManager.checkFFmpeg()
    }
}