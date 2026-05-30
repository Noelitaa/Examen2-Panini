package com.moviles.paninisupport.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.moviles.paninisupport.core.UserMessages
import com.moviles.paninisupport.data.remote.model.LoginResponseDto
import com.moviles.paninisupport.data.repository.ApiResult
import com.moviles.paninisupport.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val user: LoginResponseDto? = null,
    val errorMessage: String? = null
)

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState(errorMessage = UserMessages.Auth.LOGIN_EMPTY_FIELDS)
            return
        }
        _uiState.value = LoginUiState(isLoading = true)
        viewModelScope.launch {
            when (val result = authRepository.login(email = trimmedEmail, password = password)) {
                is ApiResult.Success -> _uiState.value = LoginUiState(user = result.data)
                is ApiResult.Error -> _uiState.value = LoginUiState(errorMessage = result.message)
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

class LoginViewModelFactory(
    private val authRepository: AuthRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        LoginViewModel(authRepository) as T
}
