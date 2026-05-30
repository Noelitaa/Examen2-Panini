package com.moviles.paninisupport.data.remote

import com.moviles.paninisupport.core.AppConstants
import com.moviles.paninisupport.data.remote.model.CreateTicketRequest
import com.moviles.paninisupport.data.remote.model.LoginRequest
import com.moviles.paninisupport.data.remote.model.LoginResponseDto
import com.moviles.paninisupport.data.remote.model.TicketDto
import com.moviles.paninisupport.data.remote.model.UpdatePriorityRequest
import com.moviles.paninisupport.data.remote.model.UpdateStatusRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST(AppConstants.Api.Paths.AUTH_LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponseDto>

    @GET(AppConstants.Api.Paths.TICKETS)
    suspend fun getTickets(): Response<List<TicketDto>>

    @GET(AppConstants.Api.Paths.TICKET_BY_ID)
    suspend fun getTicketById(@Path("id") id: String): Response<TicketDto>

    @POST(AppConstants.Api.Paths.TICKETS)
    suspend fun createTicket(@Body request: CreateTicketRequest): Response<TicketDto>

    @PATCH(AppConstants.Api.Paths.TICKET_STATUS)
    suspend fun updateTicketStatus(
        @Path("id") id: String,
        @Body request: UpdateStatusRequest
    ): Response<Unit>

    @PATCH(AppConstants.Api.Paths.TICKET_PRIORITY)
    suspend fun updateTicketPriority(
        @Path("id") id: String,
        @Body request: UpdatePriorityRequest
    ): Response<Unit>
}
