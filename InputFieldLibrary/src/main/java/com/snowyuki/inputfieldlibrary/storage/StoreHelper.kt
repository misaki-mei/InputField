package com.snowyuki.inputfieldlibrary.storage

import android.content.Context

class StoreHelper private constructor(c : Context){

    companion object {

        @Volatile private var mInstance : StoreHelper? = null

        @Synchronized fun getInstance(c : Context): StoreHelper {
            if(mInstance == null) mInstance = StoreHelper(c)
            return mInstance!!
        }
    }

    private val sp = c.getSharedPreferences("main",0)
    private val editor = sp.edit()

    fun put(key : String, value : Int){
        editor.putInt(key,value).apply()
    }

    fun put(key : String, value : Float){
        editor.putFloat(key,value).apply()
    }

    fun put(key : String, value : Long){
        editor.putLong(key,value).apply()
    }

    fun put(key : String, value : Boolean){
        editor.putBoolean(key,value).apply()
    }

    fun put(key : String, value : String){
        editor.putString(key,value).apply()
    }

    fun getInt(key: String, dfValue : Int) = sp.getInt(key,dfValue)
    fun getFloat(key: String, dfValue : Float) = sp.getFloat(key,dfValue)
    fun getString(key: String, dfValue : String) = sp.getString(key,dfValue)
    fun getLong(key: String, dfValue : Long) = sp.getLong(key,dfValue)
    fun getBoolean(key: String, dfValue : Boolean) = sp.getBoolean(key,dfValue)
}