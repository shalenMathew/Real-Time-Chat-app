package com.example.chatapplication.data.repository

import android.util.Log
import com.example.chatapplication.data.api.ChatApi
import com.example.chatapplication.data.model.profileModel.requestBody.ProfileRequestBody
import com.example.chatapplication.data.model.profileModel.responsebody.ProfileResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.example.chatapplication.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class ProfileRepository @Inject constructor(
    private val api: ChatApi
) {

    fun updateUserProfile(authToken: String, firstName: String, lastName: String): Flow<Result<ProfileResponse>> = flow {

        emit(Result.Loading())

        try {

            Log.d("TAG","token - Bearer $authToken")
            val response = api.updateProfile(token ="Bearer $authToken", ProfileRequestBody(firstName = firstName, lastName =  lastName))


            if (response.isSuccessful && response.body() != null) {
                emit(Result.Success(response.body()!!))
            } else {
                emit(Result.Error("Profile update failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Exception: ${e.localizedMessage}"))
        }

    }.flowOn(Dispatchers.IO)

}