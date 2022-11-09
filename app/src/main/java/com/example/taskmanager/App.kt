package com.example.taskmanager

import android.app.Application
import androidx.room.Room
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.local.db.AppDataBase

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(this,AppDataBase::class.java,"database").allowMainThreadQueries().build()
    }
    companion object{
        lateinit var db:AppDataBase
    }
}