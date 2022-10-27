package com.example.taskmanager.data

import java.io.Serializable

data class Task(
    var title: String? = null,
    var description: String? = null,
    var time: Long? = null
) : Serializable
