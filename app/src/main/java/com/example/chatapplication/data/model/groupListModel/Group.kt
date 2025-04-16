package com.example.chatapplication.data.model.groupListModel

data class Group(
    val _id: String,
    val description: String,
    val `file`: File,
    val latestMessage: LatestMessage,
    val name: String
)