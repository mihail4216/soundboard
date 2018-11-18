package com.example.mihail.soundboards

import android.app.Application
import android.os.Environment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import java.io.File

class App : Application() {

    var width: Int = 0
    var height: Int = 0

    fun getDirectoryApp(): File {
        val file= File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"sounds")
        if (!file.exists())
            file.mkdirs()
        return file
    }

    private var mRequestQueue: RequestQueue? = null
    val requestQueue: RequestQueue
        get() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(applicationContext)
            }
            return mRequestQueue!!
        }


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
//        Realm.init(this)
//        val config = RealmConfiguration.Builder()
//            .name("base1")
//            .deleteRealmIfMigrationNeeded() // Migration to run instead of throwing an exception
//            .build()
//        Realm.setDefaultConfiguration(config)
//        Realm.getInstance(config)
    }

    override fun onTerminate() {
//        Realm.getDefaultInstance().close()
        super.onTerminate()
    }
}