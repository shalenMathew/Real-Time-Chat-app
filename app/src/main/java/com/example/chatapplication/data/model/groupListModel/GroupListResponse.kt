package com.example.chatapplication.data.model.groupListModel

data class GroupListResponse(
    val code: Int,
    val message: String,
    val resources: Resources,
    val status: Boolean
)