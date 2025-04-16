package com.example.chatapplication.data.model.chat


data class ChatMessage(
    val id: String,
    val groupId: String,
    val content: String?,
    val type: String, // text, image, etc.
    val category: String?, // user or system
    val sender: Sender,
    val file: FileInfo?,
    val repliedTo: RepliedMessage?,
    val event: EventInfo?,
    val createdAt: String
)

data class Sender(
    val id: String,
    val firstName: String,
    val lastName: String
)

data class FileInfo(
    val originalName: String,
    val localFilePath: String,
    val mimeType: String
)

data class RepliedMessage(
    val id: String,
    val sender: Sender,
    val content: String?,
    val file: FileInfo?,
    val type: String
)

data class EventInfo(
    val id: String,
    val name: String,
    val theme: String,
    val venue: String,
    val startTime: String,
    val endTime: String,
    val graceTime: String,
    val file: FileInfo?
)
