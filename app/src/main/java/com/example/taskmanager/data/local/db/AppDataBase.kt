package com.example.taskmanager.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskmanager.data.Task

@Database(entities = arrayOf(Task::class), version = 1)
abstract class AppDataBase:RoomDatabase() {
    abstract fun dao():TaskDao
}