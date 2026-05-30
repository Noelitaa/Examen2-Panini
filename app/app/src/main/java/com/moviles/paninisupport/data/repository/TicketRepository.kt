package com.moviles.paninisupport.data.repository

import com.moviles.paninisupport.core.UserMessages
import com.moviles.paninisupport.data.mock.MockData
import com.moviles.paninisupport.data.remote.model.CreateTicketRequest
import com.moviles.paninisupport.data.remote.model.TicketDto
import com.moviles.paninisupport.data.remote.model.TicketStatus
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

interface TicketRepository {
    suspend fun getTickets(): ApiResult<List<TicketDto>>
    suspend fun getTicketById(id: String): ApiResult<TicketDto>
    suspend fun createTicket(request: CreateTicketRequest): ApiResult<TicketDto>
    suspend fun updateTicketStatus(id: String, status: String): ApiResult<Unit>
    suspend fun updateTicketPriority(id: String, priority: String): ApiResult<Unit>
}

class MockTicketRepository : TicketRepository {

    private val tickets = MockData.tickets.toMutableList()

    override suspend fun getTickets(): ApiResult<List<TicketDto>> {
        delay(600)
        return ApiResult.Success(tickets.toList())
    }

    override suspend fun getTicketById(id: String): ApiResult<TicketDto> {
        delay(300)
        val ticket = tickets.find { it.id == id }
        return if (ticket != null) {
            ApiResult.Success(ticket)
        } else {
            ApiResult.Error(UserMessages.Tickets.NOT_FOUND)
        }
    }

    override suspend fun createTicket(request: CreateTicketRequest): ApiResult<TicketDto> {
        delay(500)
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val newTicket = TicketDto(
            id = UUID.randomUUID().toString(),
            title = request.title,
            description = request.description,
            priority = request.priority,
            status = TicketStatus.OPEN,
            provider = request.provider,
            category = request.category,
            createdAt = formatter.format(Date())
        )
        tickets.add(newTicket)
        return ApiResult.Success(newTicket)
    }

    override suspend fun updateTicketStatus(id: String, status: String): ApiResult<Unit> {
        delay(300)
        val index = tickets.indexOfFirst { it.id == id }
        return if (index != -1) {
            tickets[index] = tickets[index].copy(status = status)
            ApiResult.Success(Unit)
        } else {
            ApiResult.Error(UserMessages.Tickets.NOT_FOUND)
        }
    }

    override suspend fun updateTicketPriority(id: String, priority: String): ApiResult<Unit> {
        delay(300)
        val index = tickets.indexOfFirst { it.id == id }
        return if (index != -1) {
            tickets[index] = tickets[index].copy(priority = priority)
            ApiResult.Success(Unit)
        } else {
            ApiResult.Error(UserMessages.Tickets.NOT_FOUND)
        }
    }
}
