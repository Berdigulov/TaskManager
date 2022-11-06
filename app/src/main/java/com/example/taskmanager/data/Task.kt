package com.example.taskmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var time: Long? = null
) : Serializable
