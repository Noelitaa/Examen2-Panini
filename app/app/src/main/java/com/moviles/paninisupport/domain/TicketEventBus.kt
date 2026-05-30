package com.moviles.paninisupport.domain

import com.moviles.paninisupport.data.remote.model.TicketDto
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed class TicketEvent {
    data class TicketCreated(val ticket: TicketDto) : TicketEvent()
    data class PriorityChanged(val ticketId: String, val newPriority: String) : TicketEvent()
    data class StatusChanged(val ticketId: String, val newStatus: String) : TicketEvent()
}

/**
 * Application-scoped event bus for ticket-related state changes.
 *
 * Uses a SharedFlow with a replay buffer of 0 so events are only delivered to
 * active collectors. This keeps all screens in sync without manual polling or
 * full list reloads.
 */
object TicketEventBus {
    private val _events = MutableSharedFlow<TicketEvent>(extraBufferCapacity = 10)
    val events: SharedFlow<TicketEvent> = _events.asSharedFlow()

    fun publish(event: TicketEvent) {
        _events.tryEmit(event)
    }
}
