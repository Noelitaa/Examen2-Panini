package com.moviles.paninisupport.ui.screens.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.moviles.paninisupport.data.remote.model.TicketDto
import com.moviles.paninisupport.data.remote.model.TicketPriority
import com.moviles.paninisupport.data.repository.ApiResult
import com.moviles.paninisupport.data.repository.TicketRepository
import com.moviles.paninisupport.domain.TicketEvent
import com.moviles.paninisupport.domain.TicketEventBus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TicketListUiState(
    val isLoading: Boolean = true,
    val tickets: List<TicketDto> = emptyList(),
    val errorMessage: String? = null
)

class TicketListViewModel(
    private val ticketRepository: TicketRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TicketListUiState())
    val uiState: StateFlow<TicketListUiState> = _uiState.asStateFlow()

    init {
        loadTickets()
        observeEvents()
    }

    fun loadTickets() {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            when (val result = ticketRepository.getTickets()) {
                is ApiResult.Success -> _uiState.value = TicketListUiState(
                    isLoading = false,
                    tickets = result.data.sortedByPriority()
                )
                is ApiResult.Error -> _uiState.value = TicketListUiState(
                    isLoading = false,
                    errorMessage = result.message
                )
            }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            TicketEventBus.events.collect { event ->
                when (event) {
                    is TicketEvent.TicketCreated -> {
                        val updated = (_uiState.value.tickets + event.ticket).sortedByPriority()
                        _uiState.value = _uiState.value.copy(tickets = updated)
                    }
                    is TicketEvent.PriorityChanged -> {
                        val updated = _uiState.value.tickets
                            .map { if (it.id == event.ticketId) it.copy(priority = event.newPriority) else it }
                            .sortedByPriority()
                        _uiState.value = _uiState.value.copy(tickets = updated)
                    }
                    is TicketEvent.StatusChanged -> {
                        val updated = _uiState.value.tickets
                            .map { if (it.id == event.ticketId) it.copy(status = event.newStatus) else it }
                        _uiState.value = _uiState.value.copy(tickets = updated)
                    }
                }
            }
        }
    }
}

private fun List<TicketDto>.sortedByPriority(): List<TicketDto> =
    sortedBy { TicketPriority.sortOrder[it.priority] ?: Int.MAX_VALUE }

class TicketListViewModelFactory(
    private val ticketRepository: TicketRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        TicketListViewModel(ticketRepository) as T
}
