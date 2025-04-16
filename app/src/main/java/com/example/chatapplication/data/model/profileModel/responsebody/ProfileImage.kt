package com.example.chatapplication.data.model.profileModel.responsebody

data class ProfileImage(
    val _id: String,
    val cloudUrl: String,
    val localFilePath: String,
    val mimeType: String,
    val originalName: String
)