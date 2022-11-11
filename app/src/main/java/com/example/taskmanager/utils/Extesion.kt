package com.example.taskmanager.data

import android.os.Bundle
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide

fun ImageView.loadImage(url:String){
    Glide.with(this).load(url).into(this)
}

