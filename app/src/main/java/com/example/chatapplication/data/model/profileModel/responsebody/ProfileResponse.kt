package com.example.chatapplication.data.model.profileModel.responsebody

data class ProfileResponse(
    val code: Int,
    val message: String,
    val resources: Resources,
    val status: Boolean
)