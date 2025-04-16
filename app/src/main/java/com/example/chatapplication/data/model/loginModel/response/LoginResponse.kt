package com.example.chatapplication.data.model.loginModel.response

data class LoginResponse(
    val code: Int,
    val message: String,
    val resources: Resources,
    val status: Boolean
)