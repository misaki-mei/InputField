package com.snowyuki.inputfieldlibrary.storage

import android.arch.persistence.room.Room
import android.content.Context

fun Context.withFieldDB(f : (FieldDataDB)->Unit){
    val fieldDB = Room.databaseBuilder(this,FieldDataDB::class.java, "fieldData.db").build()
    f.invoke(fieldDB)
    fieldDB.close()
}
