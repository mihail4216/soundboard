package com.example.mihail.soundboards.adapter

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mihail.soundboards.App
import com.example.mihail.soundboards.R
import com.example.mihail.soundboards.manager.AudioPlayer
import com.example.mihail.soundboards.models.MusicModel
import java.util.*


class MusicListAdapter(var array: Array<MusicModel>) : RecyclerView.Adapter<MusicListAdapter.MusicViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MusicViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.music_view_holder, p0, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(p0: MusicViewHolder, p1: Int) {

        p0.onBind(array[p1])
    }

    override fun getItemCount(): Int {
        return array.size
    }

    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var mTxtName: TextView
        private lateinit var mImageMusic: ImageView
        private lateinit var mBtnShare: ImageView

        fun onBind(musicModel: MusicModel) {
            mTxtName = itemView.findViewById(R.id.txt)
            mImageMusic = itemView.findViewById(R.id.img)
            mBtnShare = itemView.findViewById(R.id.share)
            mTxtName.text = musicModel.name
            mImageMusic.setOnClickListener {
                AudioPlayer.instance.play(Environment.DIRECTORY_DOWNLOADS+"/"+musicModel.music_url)
            }

            val coef = App.instance.width / 3
            mImageMusic.layoutParams.width = coef
            mImageMusic.layoutParams.height = App.instance.height / 3


            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(1))
            Glide.with(itemView.context)
                .load(musicModel.img_url)
                .apply(requestOptions)
                .into(mImageMusic)

            mBtnShare.setOnClickListener {



//                FFmpegManager.createAudio(musicModel.music_url)
            }
        }


    }
}