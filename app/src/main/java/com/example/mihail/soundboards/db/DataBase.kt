package com.example.mihail.soundboards.db

import com.vicpin.krealmextensions.AutoIncrementPK
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MusicTable(
    var name: String = "",
    var music_url: String = "",
    var file_uri: String = "",
    var author: String = "",
    var img_url: String = ""

) : RealmObject()