package com.example.categoryresponse.models

data class UserResponse(
    val `data`: User,
    val error: Boolean
)

data class User(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val firstName: String,
    val mobile: String,
    val password: String
)