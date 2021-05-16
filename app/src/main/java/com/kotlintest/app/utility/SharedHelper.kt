package com.kotlintest.app.utility

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences

class SharedHelper  constructor(private val application : Application) {

    fun putInUser(Key: String, Value: String) {
        preferences = application.getSharedPreferences(PREF_USER, Activity.MODE_PRIVATE)
        editor = preferences.edit()
        editor.putString(Key, Value)
        editor.apply()
    }

    fun getFromUser(Key: String): String {
        preferences = application.getSharedPreferences(PREF_USER, Activity.MODE_PRIVATE)
        return preferences.getString(Key,"")!!
    }

    fun putInCache(Key: String, Value: String) {
        preferences = application.getSharedPreferences(PREF_CACHE, Activity.MODE_PRIVATE)
        editor = preferences.edit()
        editor.putString(Key, Value)
        editor.apply()
    }

    fun getFromCache(Key: String): String {
        preferences = application.getSharedPreferences(PREF_CACHE, Activity.MODE_PRIVATE)
        return preferences.getString(Key, "")!!
    }

    fun clearCache() {
        preferences = application.getSharedPreferences(PREF_CACHE, Activity.MODE_PRIVATE)
        preferences.edit().clear().apply()
    }

    fun clearUser() {
        preferences = application.getSharedPreferences(PREF_USER, Activity.MODE_PRIVATE)
        preferences.edit().clear().apply()
    }

  /*  fun putModel(model : Any ,Key: String){
        preferences = application.getSharedPreferences(PREF_USER, Activity.MODE_PRIVATE)
        editor = preferences.edit()
        var type  =gson.toJson(model)
        editor.putString(Key, type)
        editor.apply()

    }*/

    fun getModel(Key: String): String {
        preferences = application.getSharedPreferences(PREF_USER, Activity.MODE_PRIVATE)
        return preferences.getString(Key, "")!!
    }

    companion object {
        private lateinit var preferences: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        private const val PREF_USER = "USER"
        private const val PREF_CACHE = "CACHE"
    }

}
