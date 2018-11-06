package com.example.mihail.soundboards.activity

import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.bottomappbar.BottomAppBar
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.example.mihail.soundboard.manager.FirebaseManager
import com.example.mihail.soundboards.App
import com.example.mihail.soundboards.R
import com.example.mihail.soundboards.adapter.MusicListAdapter
import com.example.mihail.soundboards.manager.FFmpegManager

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
            mMusicListView.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
            mMusicListView.adapter = MusicListAdapter(it)

        }
        FFmpegManager.createAudio(this,"")



    }
}
