package com.moviles.paninisupport.navigation

import android.net.Uri

object AppDestinations {
    const val LOGIN = "login"
    const val TICKET_LIST = "ticketList"
    const val TICKET_DETAIL = "ticketDetail"
    const val CREATE_TICKET = "createTicket"

    fun ticketDetailRoute(ticketId: String): String =
        "$TICKET_DETAIL/${Uri.encode(ticketId)}"
}
