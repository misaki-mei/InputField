package com.snowyuki.inputfieldlibrary.storage

import android.arch.persistence.room.*

@Dao
interface FieldDataDao {

    @Insert
    fun insertData(vararg fieldData: FieldData)

    @Insert
    fun insertData(fieldData: List<FieldData>)

    @Update
    fun updateData(vararg fieldData: FieldData)

    @Delete
    fun deleteData(vararg fieldData: FieldData)

    @Query("select * from fieldData where 'key' = :key")
    fun getData(key : String) : List<FieldData>

    @Query("select 'value' from fieldData where 'key' = :key")
    fun getValues(key : String) : List<String>

    @Query("delete from fieldData where `key` = :key")
    fun deleteData(key: String)
}