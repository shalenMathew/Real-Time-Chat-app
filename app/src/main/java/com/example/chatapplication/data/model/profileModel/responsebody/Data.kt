package com.example.chatapplication.data.model.profileModel.responsebody

data class Data(
    val _id: String,
    val dialCode: String,
    val email: String,
    val firstName: String,
    val interests: List<Interest>,
    val isActive: Boolean,
    val lastName: String,
    val mobileNumber: Long,
    val profileImage: ProfileImage,
    val roles: List<Role>
)