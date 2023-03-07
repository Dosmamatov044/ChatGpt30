package com.example.chatgpt30

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide
        .with(this.context)
        .load(url)
        .centerCrop()
        .into(this)
}



fun Context.showToast(text:String){


    Toast.makeText(this,text, Toast.LENGTH_SHORT).show()


}