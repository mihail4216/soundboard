package com.example.mihail.soundboards.manager

import android.content.Context
import android.util.Log
import com.example.mihail.soundboards.App
import nl.bravobit.ffmpeg.FFcommandExecuteResponseHandler
import nl.bravobit.ffmpeg.FFmpeg

class FFmpegManager {

    companion object {

        fun checkFFmpeg(){
            FFmpeg.getInstance(App.instance.applicationContext).isSupported
        }
        fun createAudio(context: Context, url: String) {
            val cmd = "ffmpeg -version"
            FFmpeg.getInstance(context).execute(arrayOf(cmd), listenerFFmpeg)
        }

        fun createAudio(url: String) {
            val cmd = ""
            FFmpeg.getInstance(App.instance.applicationContext).execute(arrayOf(cmd), listenerFFmpeg)
        }


        private var listenerFFmpeg = object : FFcommandExecuteResponseHandler {
            override fun onFinish() {
                Log.d("ffmpeg", "onFinish")
            }

            override fun onSuccess(message: String?) {
                Log.d("ffmpeg", "onSuccess:$message")

            }

            override fun onFailure(message: String?) {
                Log.d("ffmpeg", "onFailure:$message")

            }

            override fun onProgress(message: String?) {
                Log.d("ffmpeg", "onProgress:$message")

            }

            override fun onStart() {
                Log.d("ffmpeg", "onStart")

            }

        }
    }
}