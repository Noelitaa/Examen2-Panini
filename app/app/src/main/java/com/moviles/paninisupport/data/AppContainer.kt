package com.moviles.paninisupport.data

import com.moviles.paninisupport.data.remote.RetrofitClient
import com.moviles.paninisupport.data.repository.AuthRepository
import com.moviles.paninisupport.data.repository.MockTicketRepository
import com.moviles.paninisupport.data.repository.TicketRepository

/**
 * Manual dependency container — wires repositories together without a DI framework.
 *
 * Swap [ticketRepository] to [RemoteTicketRepository(RetrofitClient.apiService)] when the
 * backend is ready. No other file needs to change.
 */
object AppContainer {

    val authRepository: AuthRepository = AuthRepository()

    val ticketRepository: TicketRepository = MockTicketRepository()

    @Suppress("unused")
    val apiService = RetrofitClient.apiService
}
