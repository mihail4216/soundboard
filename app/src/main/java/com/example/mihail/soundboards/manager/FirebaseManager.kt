package com.example.mihail.soundboards.manager

import android.util.Log
import com.example.mihail.soundboards.models.MusicModel
import com.google.firebase.database.*
import com.google.gson.GsonBuilder
import org.json.JSONObject
import java.lang.Exception
import java.util.*


class FirebaseManager {

    companion object {

        private val TAG = "man"

        fun getArrayMusic(sec: (Array<MusicModel>) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("table_music")

//         addMusic(myRef)
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    Log.d(TAG, getJson(p0.value).toString())
                    try {
                        val arrayMusic = GsonBuilder().create()
                            .fromJson(getJson(p0.value).getJSONArray("music").toString(), Array<MusicModel>::class.java)
                        val completeArray = ArrayList<MusicModel>()
                        for (i in arrayMusic) {
                            if (i.isShow) {
                                completeArray.add(i)
                                FirebaseDatabase.getInstance().goOffline()
                            }
                        }
                        sec(completeArray.toTypedArray())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
        }

        private fun getJson(value: Any?): JSONObject {
            return JSONObject((value as HashMap<Any, Any>))
        }

        private fun addMusic(myRef: DatabaseReference) {
            var collection = HashMap<String, Any>()

            var arr = ArrayList<HashMap<String, Any>>()
            for (i in 0..10) {
                var music = HashMap<String, Any>()
                music["name"] = "gala_$i"
                music["music_url"] = "ahah_$i"
                music["author"] = "mihail_$i"
                music["img_url"] = "alalalaldld_$i"
                arr.add(music)
            }
            collection["music"] = arr
            myRef.setValue(collection)
        }
    }
}