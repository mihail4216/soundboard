package com.example.mihail.soundboards.adapter

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mihail.soundboards.App
import com.example.mihail.soundboards.R
import com.example.mihail.soundboards.helpers.DisplayHelper
import com.example.mihail.soundboards.manager.AudioPlayer
import com.example.mihail.soundboards.models.MusicModel
import com.example.mihail.soundboards.service.FileLoader
import java.io.File
import java.io.FileOutputStream


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
        private lateinit var mImageMusic: ImageView
        //        private lateinit var mImageMusic: VideoView
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
            itemView.setOnClickListener {
                //                mImageMusic.start()
//                AudioPlayer.instance.play("${Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)}/${musicModel.author}_${musicModel.name}.mp4")
                val file = File(App.instance.getDirectoryApp(), "${musicModel.author}_${musicModel.name}.mp4")
                if (!file.exists())
                    FileLoader.load(musicModel.music_url) { _, arr ->

                        AudioPlayer.instance.play(Uri.fromFile(save(arr!!, "mp4", musicModel)).toString())
                    }
                else {
                        AudioPlayer.instance.play(Uri.fromFile(file).toString())

                }

            }

            val gifWidth =
                DisplayHelper.widthPixels / 2
            val gifHeigth = musicModel.height * gifWidth / musicModel.width

            mImageMusic.layoutParams.height = gifHeigth
            mImageMusic.layoutParams.width = gifWidth


            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(1))
            Glide.with(itemView.context)
                .load(musicModel.img_url)
                .apply(requestOptions)
                .into(mImageMusic)

            mBtnShare.setOnClickListener {
                //
//                listenerMusic.onShare(Uri.parse("${Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)}/${musicModel.author}_${musicModel.name}.mp4"))
                val dialog = ProgressDialog(listenerMusic.activity)
                dialog.show()
                FileLoader.load(musicModel.music_url) { e, arr ->
                    dialog.dismiss()
                    listenerMusic.onShare(save(arr!!, "mp4", musicModel)!!)
                }
            }
        }


        private fun save(videoByte: ByteArray, format: String, musicModel: MusicModel): File? {

            val name = "${musicModel.author}_${musicModel.name}.$format"
            val fileMusic = File(App.instance.getDirectoryApp(), "${musicModel.author}_${musicModel.name}.$format")
            return if (!fileMusic.exists()) {
                val file = File(App.instance.getDirectoryApp(), name)
                val fos = FileOutputStream(file)
                fos.write(videoByte)
                fos.close()
                file
            } else
                fileMusic

        }
    }

    interface ItemMusicListener {
        var activity: Context?

        fun onShare(file: File)

    }

}