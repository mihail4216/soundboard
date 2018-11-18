package com.example.mihail.soundboards.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import com.example.mihail.soundboards.App
import com.example.mihail.soundboards.R
import com.example.mihail.soundboards.models.MusicModel


class MusicListAdapter(var array: Array<MusicModel>, var listenerMusic: ItemMusicListener) :
    RecyclerView.Adapter<MusicListAdapter.MusicViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MusicViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.music_view_holder, p0, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(p0: MusicViewHolder, p1: Int) {

        p0.onBind(array[p1], listenerMusic)
    }

    override fun getItemCount(): Int {
        return array.size
    }

    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var mTxtName: TextView
        //        private lateinit var mImageMusic: ImageView
        private lateinit var mImageMusic: VideoView
        private lateinit var mBtnShare: ImageView

        @SuppressLint("ClickableViewAccessibility")
        fun onBind(
            musicModel: MusicModel,
            listenerMusic: ItemMusicListener
        ) {
            mTxtName = itemView.findViewById(R.id.txt)
            mImageMusic = itemView.findViewById(R.id.img)
            mBtnShare = itemView.findViewById(R.id.share)
            mTxtName.text = musicModel.name
//            mImageMusic.setOnClickListener {
//                AudioPlayer.instance.play("${Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)}/${musicModel.author}_${musicModel.name}.mp4")
//            }
            mImageMusic.setVideoURI(Uri.parse(musicModel.music_url))
            var controler = MediaController(listenerMusic.activity)
//            controler.hide()
//            controler.visibility = View.GONE
            mImageMusic.setMediaController(controler)
            mImageMusic.requestFocus(0)
            mImageMusic.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN)
                    if (mImageMusic.isPlaying)
                        mImageMusic.stopPlayback()
                    else
                        mImageMusic.start()
                true
            }

            val coef = App.instance.width / 2
//            mImageMusic.layoutParams.width = coef
//            mImageMusic.layoutParams.height = App.instance.height / 3


//            var requestOptions = RequestOptions()
//            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(1))
//            Glide.with(itemView.context)
//                .load(musicModel.img_url)
//                .apply(requestOptions)
//                .into(mImageMusic)

            mBtnShare.setOnClickListener {
                //
//                var shareIntent = Intent(Intent.ACTION_SEND)
//                shareIntent.type = "audio/mp3"
//                shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse("${Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)}/${musicModel.author}_${musicModel.name}.mp3"))
                listenerMusic.onShare(Uri.parse("${Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)}/${musicModel.author}_${musicModel.name}.mp4"))
//                FFmpegManager.createAudio(musicModel.music_url)
            }
        }
    }

    interface ItemMusicListener {
        var activity: Context?

        fun onShare(parse: Uri)

    }

}