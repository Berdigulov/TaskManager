package com.example.taskmanager.data.local

import android.content.Context
import android.content.SharedPreferences




class PreferenceManager {
    private var INSTANCE: PreferenceManager? = null
    private var preferences: SharedPreferences? = null

    @Synchronized
    fun getInstance(context: Context): PreferenceManager? {
        if (INSTANCE == null) {
            INSTANCE = PreferenceManager()
            preferences = context.getSharedPreferences(PREF_MAN, Context.MODE_PRIVATE)
        }
        return INSTANCE
    }

    fun setString(key: String?, value: String?) {
        preferences!!.edit().putString(key, value).apply()
    }

    fun getString(key: String?): String? {
        return preferences!!.getString(key, "")
    }

    companion object{
        private const val PREF_MAN = "pref.man"
    }
}