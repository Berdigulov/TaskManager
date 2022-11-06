package com.example.taskmanager.data.local

import android.content.Context
import android.content.SharedPreferences

class Pref(private var context: Context) {
    private var pref: SharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)

    fun isBoardingShow():Boolean{
        return pref.getBoolean(SHOW_BOARDING,false)
    }

    fun saveBoardingShow(isShow: Boolean){
        pref.edit().putBoolean(SHOW_BOARDING,isShow).apply()

    }

    fun getName(): String? {
        return pref.getString(USER_NAME, "")
    }

    fun saveName(name:String){
        pref.edit().putString(USER_NAME,name).apply()

    }

    fun getAge(): String? {
        return pref.getString(USER_AGE, "")
    }

    fun saveAge(age:String){
        pref.edit().putString(USER_AGE,age).apply()

    }

    companion object{
        private const val PREF_NAME = "pref.task"
        private const val SHOW_BOARDING = "boarding"
        private const val USER_NAME = "pref.name"
        private const val USER_AGE = "pref.age"
    }
}