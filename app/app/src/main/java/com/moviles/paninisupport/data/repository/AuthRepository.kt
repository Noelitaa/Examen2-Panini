package com.moviles.paninisupport.data.repository

import com.moviles.paninisupport.core.UserMessages
import com.moviles.paninisupport.data.remote.model.LoginResponseDto
import kotlinx.coroutines.delay

class AuthRepository {

    private var currentSession: LoginResponseDto? = null

    suspend fun login(email: String, password: String): ApiResult<LoginResponseDto> {
        delay(800)
        val trimmedEmail = email.trim()
        return if (trimmedEmail.contains("@") && password.length >= 4) {
            val session = LoginResponseDto(
                token = "mock-jwt-${System.currentTimeMillis()}",
                name = resolveDisplayName(trimmedEmail),
                email = trimmedEmail,
                role = "SUPPORT_AGENT"
            )
            currentSession = session
            ApiResult.Success(session)
        } else {
            ApiResult.Error(UserMessages.Auth.INVALID_CREDENTIALS)
        }
    }

    fun clearSession() {
        currentSession = null
    }

    fun currentUser(): LoginResponseDto? = currentSession

    private fun resolveDisplayName(email: String): String {
        val local = email.substringBefore("@")
        return local.split(".", "_", "-")
            .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
    }
}
