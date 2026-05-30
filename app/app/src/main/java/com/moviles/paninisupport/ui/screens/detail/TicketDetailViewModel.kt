package com.moviles.paninisupport.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.moviles.paninisupport.data.remote.model.TicketDto
import com.moviles.paninisupport.data.repository.ApiResult
import com.moviles.paninisupport.data.repository.TicketRepository
import com.moviles.paninisupport.domain.TicketEvent
import com.moviles.paninisupport.domain.TicketEventBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TicketDetailUiState(
    val isLoading: Boolean = true,
    val ticket: TicketDto? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class TicketDetailViewModel(
    private val ticketRepository: TicketRepository,
    private val ticketId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(TicketDetailUiState())
    val uiState: StateFlow<TicketDetailUiState> = _uiState.asStateFlow()

    init {
        loadTicket()
    }

    private fun loadTicket() {
        viewModelScope.launch {
            when (val result = ticketRepository.getTicketById(ticketId)) {
                is ApiResult.Success -> _uiState.value = TicketDetailUiState(ticket = result.data)
                is ApiResult.Error -> _uiState.value = TicketDetailUiState(errorMessage = result.message)
            }
        }
    }

    fun updateStatus(newStatus: String) {
        viewModelScope.launch {
            when (val result = ticketRepository.updateTicketStatus(ticketId, newStatus)) {
                is ApiResult.Success -> {
                    val updated = _uiState.value.ticket?.copy(status = newStatus)
                    _uiState.value = _uiState.value.copy(
                        ticket = updated,
                        successMessage = "Status updated to $newStatus"
                    )
                    TicketEventBus.publish(TicketEvent.StatusChanged(ticketId, newStatus))
                }
                is ApiResult.Error -> _uiState.value = _uiState.value.copy(errorMessage = result.message)
            }
        }
    }

    fun updatePriority(newPriority: String) {
        viewModelScope.launch {
            when (val result = ticketRepository.updateTicketPriority(ticketId, newPriority)) {
                is ApiResult.Success -> {
                    val updated = _uiState.value.ticket?.copy(priority = newPriority)
                    _uiState.value = _uiState.value.copy(
                        ticket = updated,
                        successMessage = "Priority updated to $newPriority"
                    )
                    TicketEventBus.publish(TicketEvent.PriorityChanged(ticketId, newPriority))
                }
                is ApiResult.Error -> _uiState.value = _uiState.value.copy(errorMessage = result.message)
            }
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(errorMessage = null, successMessage = null)
    }
}

class TicketDetailViewModelFactory(
    private val ticketRepository: TicketRepository,
    private val ticketId: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TicketDetailViewModel(ticketRepository, ticketId) as T
}
