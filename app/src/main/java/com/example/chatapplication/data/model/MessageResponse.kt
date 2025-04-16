package com.example.chatapplication.data.model

data class MessageResponse(
    val status: Boolean, // Indicates if the message was successfully sent
    val code: Int, // Status code, typically 200 for success
    val message: String // A message indicating success or failure
)