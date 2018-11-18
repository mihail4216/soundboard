package com.example.mihail.soundboards.activity

import android.app.DownloadManager
import android.app.DownloadManager.Request.VISIBILITY_HIDDEN
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.example.mihail.soundboards.App
import com.example.mihail.soundboards.R
import com.example.mihail.soundboards.adapter.MusicListAdapter
import com.example.mihail.soundboards.manager.FFmpegManager
import com.example.mihail.soundboards.manager.FirebaseManager
import com.example.mihail.soundboards.models.MusicModel
import java.io.File

class MainActivity() : AppCompatActivity(), MusicListAdapter.ItemMusicListener {
    override fun onShare(file: File) {

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "video/*"
//        File(parse.toString())  Uri.fromFile(File(parse.toString()))
//        var file = File(parse.toString())
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
        startActivity(Intent.createChooser(shareIntent, "Выберете через что отправить"))
    }

    override var activity: Context? = this

    private lateinit var mMusicListView: RecyclerView
//    private lateinit var mBottomAppBar: BottomAppBar
//    private lateinit var mFloatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        mBottomAppBar = findViewById(R.id.bar)
        mMusicListView = findViewById(R.id.music_list)
//        mFloatingActionButton = findViewById(R.id.fab)

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        App.instance.width = size.x
        App.instance.height = size.y

//        mFloatingActionButton.backgroundTintList = resources.getColorStateList(R.color.fab_color)
//        mBottomAppBar.backgroundTint = resources.getColorStateList(R.color.bar_color)
//        setSupportActionBar(mBottomAppBar)

        FirebaseManager.getArrayMusic {
            mMusicListView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            mMusicListView.adapter = MusicListAdapter(it, this)


        }
        FFmpegManager.createAudio(this, "")


    }

    fun saveInDb(listModel: Array<MusicModel>): Array<MusicModel> {

        for (model in listModel) {
            val nameFile = "${model.author}_${model.name}.mp4"
            val file = File(App.instance.getDirectoryApp(), nameFile)
            if (!file.exists()) {
                val request = DownloadManager.Request(Uri.parse(model.music_url))
                request.setAllowedNetworkTypes((DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE))
                request.setAllowedOverRoaming(false)
                request.setTitle(model.name)
                request.setDescription(model.author)
                request.setNotificationVisibility(VISIBILITY_HIDDEN)
                request.setVisibleInDownloadsUi(false)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameFile)
                val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                downloadManager.enqueue(request)

            }
        }

        return listModel

    }
}
