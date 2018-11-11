package com.example.mihail.soundboards.activity

import android.app.DownloadManager
import android.content.Context
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.design.bottomappbar.BottomAppBar
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.example.mihail.soundboard.manager.FirebaseManager
import com.example.mihail.soundboards.App
import com.example.mihail.soundboards.R
import com.example.mihail.soundboards.adapter.MusicListAdapter
import com.example.mihail.soundboards.db.MusicTable
import com.example.mihail.soundboards.manager.FFmpegManager
import com.example.mihail.soundboards.models.MusicModel
import com.vicpin.krealmextensions.deleteAll
import com.vicpin.krealmextensions.query
import com.vicpin.krealmextensions.queryAll
import io.realm.Realm
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mMusicListView: RecyclerView
    private lateinit var mBottomAppBar: BottomAppBar
    private lateinit var mFloatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBottomAppBar = findViewById(R.id.bar)
        mMusicListView = findViewById(R.id.music_list)
        mFloatingActionButton = findViewById(R.id.fab)

        MusicTable().deleteAll()
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        App.instance.width = size.x
        App.instance.height = size.y

        mFloatingActionButton.backgroundTintList = resources.getColorStateList(R.color.fab_color)
        mBottomAppBar.backgroundTint = resources.getColorStateList(R.color.bar_color)
        setSupportActionBar(mBottomAppBar)

        FirebaseManager.getArrayMusic {
            //            mMusicListView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//            saveInDb(it)

            mMusicListView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            mMusicListView.adapter = MusicListAdapter(saveInDb(it))


        }
        FFmpegManager.createAudio(this, "")


    }

    fun saveInDb(listModel: Array<MusicModel>): Array<MusicModel> {

        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        for (model in listModel) {
            val table = MusicTable().query {
                equalTo("name", model.name)
            }
            if (realm.where(MusicTable::class.java).like("тфь", model.music_url).findAll().size == 0) {
//            if (table.isEmpty()) {
                val nameFile = "${model.author}_${Date().time}.mp3"
                val request = DownloadManager.Request(Uri.parse(model.music_url))
                request.setAllowedNetworkTypes((DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE))
                request.setAllowedOverRoaming(false)
                request.setTitle(model.name)
                request.setDescription(model.author)
//                request.setVisibleInDownloadsUi(false)
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameFile)
                val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                downloadManager.enqueue(request)

                MusicTable(model.name, model.music_url, nameFile, model.author, model.img_url)

            }
        }
        realm.commitTransaction()
        val array = arrayOfNulls<MusicModel>(listModel.size)

        val base = MusicTable().queryAll()
        for (i in 0..base.size) {

//            array[i]!!.author = base[i].author
//            array[i]!!.name = base[i].name
//            array[i]!!.img_url = base[i].img_url
//            array[i]!!.music_url = base[i].file_uri
//            array[i]!!.isShow = true
            array[i] = MusicModel(base[i].name, base[i].file_uri, base[i].author, base[i].img_url, true)
        }
        return array as Array<MusicModel>
//        val array = base.toTypedArray()

    }
}
