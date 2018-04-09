package com.snowyuki.inputfieldlibrary.storage

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [FieldData::class],version = 1)
abstract class FieldDataDB : RoomDatabase(){
    abstract fun fieldDataDao() : FieldDataDao
}