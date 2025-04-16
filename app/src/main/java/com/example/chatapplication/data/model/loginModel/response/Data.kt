package com.example.chatapplication.data.model.loginModel.response

data class Data(
    val _id: String,
    val authToken: String,
    val dialCode: String,
    val email: String,
    val firstName: String,
    val interests: List<Interest>,
    val lastName: String,
    val mobileNumber: Long,
    val onboardingPending: Boolean,
    val profileImage: ProfileImage,
    val referCodeUsed: String,
    val refreshToken: String,
    val roles: List<Role>
)