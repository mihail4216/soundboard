package com.example.mihail.soundboards.manager


import android.media.AudioManager
import android.media.MediaPlayer

class AudioPlayer private constructor() {


    private var player: MediaPlayer? = null

    private object Holder {
        val INSTANCE = AudioPlayer()
    }

    companion object {
        val instance: AudioPlayer by lazy { Holder.INSTANCE }
    }

    fun getDuration(): Int {
        if (player != null)
            return player!!.duration
        return 0
    }

    fun play(url: String) {
        release()
        player = MediaPlayer()
        player!!.setOnPreparedListener {
            it!!.start()
        }
        player!!.setDataSource(url)
        player!!.setAudioStreamType(AudioManager.STREAM_MUSIC)

        player!!.prepareAsync()
    }

    fun release() {
        if (player != null)
            player?.release()

    }


}
