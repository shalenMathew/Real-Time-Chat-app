package com.example.chatapplication.data.repository


import com.example.chatapplication.data.api.ChatApi
import com.example.chatapplication.data.model.loginModel.requestBody.LoginRequestBody
import com.example.chatapplication.data.model.loginModel.response.LoginResponse
import com.example.chatapplication.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: ChatApi  // provide by hilt from modules
) {
    fun loginUser(mobile: Long): Flow<Result<LoginResponse>> = flow {

        emit(Result.Loading())

        try {
            val response = api.login(LoginRequestBody(mobileNumber = mobile))

            if (response.isSuccessful && response.body() != null) {

                emit(Result.Success(response.body()!!))

            } else {
                emit(Result.Error("Login failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            emit(Result.Error("Exception: ${e.localizedMessage}"))
        }
    }.flowOn(Dispatchers.IO)

}