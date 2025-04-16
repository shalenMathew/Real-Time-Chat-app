package com.example.chatapplication.data.repository

import android.util.Log
import com.example.chatapplication.data.api.ChatApi
import com.example.chatapplication.data.model.groupListModel.Data
import com.example.chatapplication.utils.Result
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ChatRepository @Inject constructor(private val api: ChatApi) {

    suspend fun fetchGroups(token: String): Result<List<Data>> {
        return try {
            Log.d("TAG", "Fetching group list...")
            val response = api.getGroupList(authToken = "Bearer $token")

            if (response.isSuccessful && response.body()?.status == true) {
                val groups = response.body()!!.resources.data
                Log.d("TAG", "Group list fetched successfully: ${groups.size} groups")
                Result.Success(groups)
            } else {
                val errorMessage = response.body()?.message ?: "Failed to load groups"
                Log.e("TAG", "Error fetching groups: $errorMessage")
                Result.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("TAG", "Exception while fetching groups: ${e.message}", e)
            Result.Error(e.message)
        }
    }

    suspend fun sendMessage(
        token: String,
        groupId: String,
        content: String?,
        file: MultipartBody.Part?
    ): Result<String> {
        return try {
            Log.d("TAG", "Preparing message for groupId=$groupId with content=$content and file=${file != null}")

            val groupBody = groupId.toRequestBody("text/plain".toMediaTypeOrNull())
            val contentBody = content?.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = api.sendMessage(
                authToken = "Bearer $token",
                group = groupBody,
                content = contentBody,
                file = file
            )

            if (response.isSuccessful && response.body()?.status == true) {
                val successMessage = response.body()?.message ?: "Message sent"
                Log.d("TAG", "Message sent successfully: $successMessage")
                Result.Success(successMessage)
            } else {
                val errorMessage = response.body()?.message ?: "Failed to send message"
                Log.e("TAG", "Error sending message: $errorMessage")
                Result.Error(errorMessage)
            }
        } catch (e: Exception) {
            Log.e("TAG", "Exception while sending message: ${e.message}", e)
            Result.Error(e.message ?: "Something went wrong")
        }
    }
}
