package com.example.chatapplication.data.api

import com.example.chatapplication.data.model.MessageResponse
import com.example.chatapplication.data.model.groupListModel.GroupListResponse
import com.example.chatapplication.data.model.loginModel.requestBody.LoginRequestBody
import com.example.chatapplication.data.model.loginModel.response.LoginResponse
import com.example.chatapplication.data.model.profileModel.requestBody.ProfileRequestBody
import com.example.chatapplication.data.model.profileModel.responsebody.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ChatApi {

    @POST("api/v1/user/auth")
    suspend fun login(@Body request: LoginRequestBody): Response<LoginResponse>

    @PUT("api/v2/user/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: ProfileRequestBody
    ): Response<ProfileResponse>


    @GET("api/v1/group/list")
    suspend fun getGroupList(
        @Query("search") search: String? = null,
        @Header("Authorization") authToken: String
    ): Response<GroupListResponse>



    @Multipart
    @POST("/api/v1/message/send")
    suspend fun sendMessage(
        @Header("Authorization") authToken: String,
        @Part("group") group: RequestBody,
        @Part("content") content: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Response<MessageResponse>

}
