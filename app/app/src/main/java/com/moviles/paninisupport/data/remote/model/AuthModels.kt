package com.moviles.paninisupport.data.remote.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponseDto(
    val token: String,
    val name: String,
    val email: String,
    val role: String
)
