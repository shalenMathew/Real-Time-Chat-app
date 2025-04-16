package com.example.chatapplication.data.model.profileModel.requestBody

data class ProfileRequestBody(
    val firstName: String,
    val lastName: String,
    val referCodeUsed: String = "SYS1234"
)