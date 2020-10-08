package com.magician.retrofitdemo


import com.google.gson.annotations.SerializedName

data class AlbumItem(
    val id: Int,
    val title: String,
    val userId: Int
)