package com.example.chatapplication.data.repository


import com.example.chatapplication.data.api.ChatApi
import com.example.chatapplication.data.model.loginModel.requestBody.LoginRequestBody
import com.example.chatapplication.data.model.loginModel.response.LoginResponse
import com.example.chatapplication.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
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
            val errorMessage = when (e) {
                is SocketTimeoutException -> "Server not responding. Issue is on the server side"
                is ConnectException -> "Unable to connect to the server. Please check your internet connection."
                is UnknownHostException -> "Server unreachable. Please ensure you're connected to the internet."
                else -> "Unexpected error occurred: ${e.localizedMessage}"
            }
            emit(Result.Error("Exception: $errorMessage"))
        }
    }.flowOn(Dispatchers.IO)

}