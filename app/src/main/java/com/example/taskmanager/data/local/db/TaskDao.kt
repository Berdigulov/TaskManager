package com.example.taskmanager.data.local.db

import androidx.room.*
import com.example.taskmanager.data.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll():List<Task>

    @Insert
    fun insert(task: Task?)

    @Update
    fun update(task: Task?)

    @Delete
    fun delete(task: Task?)
}