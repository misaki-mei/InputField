package com.snowyuki.inputfieldlibrary.storage

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "fieldData")
data class FieldData(
        @ColumnInfo(name = "_id")
        @PrimaryKey(autoGenerate = true)
        var id : Int,

        @ColumnInfo(name = "key")
        var key : String,

        @ColumnInfo(name = "value")
        var value : String
){
    @Ignore
    constructor():this(0,"","")
}