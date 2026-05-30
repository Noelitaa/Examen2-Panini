package com.moviles.paninisupport.ui.screens.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.moviles.paninisupport.core.UserMessages
import com.moviles.paninisupport.data.remote.model.CreateTicketRequest
import com.moviles.paninisupport.data.remote.model.TicketCategory
import com.moviles.paninisupport.data.remote.model.TicketPriority
import com.moviles.paninisupport.data.repository.ApiResult
import com.moviles.paninisupport.data.repository.TicketRepository
import com.moviles.paninisupport.domain.TicketEvent
import com.moviles.paninisupport.domain.TicketEventBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CreateTicketUiState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
)

class CreateTicketViewModel(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTicketUiState())
    val uiState: StateFlow<CreateTicketUiState> = _uiState.asStateFlow()

    fun createTicket(
        title: String,
        description: String,
        priority: String,
        provider: String,
        category: String
    ) {
        if (title.isBlank() || description.isBlank() || provider.isBlank()) {
            _uiState.value = CreateTicketUiState(errorMessage = UserMessages.Tickets.CREATE_EMPTY_FIELDS)
            return
        }
        _uiState.value = CreateTicketUiState(isLoading = true)
        viewModelScope.launch {
            val request = CreateTicketRequest(
                title = title.trim(),
                description = description.trim(),
                priority = priority.ifBlank { TicketPriority.MEDIUM },
                provider = provider.trim(),
                category = category.ifBlank { TicketCategory.DISTRIBUTION }
            )
            when (val result = ticketRepository.createTicket(request)) {
                is ApiResult.Success -> {
                    TicketEventBus.publish(TicketEvent.TicketCreated(result.data))
                    _uiState.value = CreateTicketUiState(success = true)
                }
                is ApiResult.Error -> {
                    _uiState.value = CreateTicketUiState(errorMessage = result.message)
                }
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

class CreateTicketViewModelFactory(
    private val ticketRepository: TicketRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CreateTicketViewModel(ticketRepository) as T
}
