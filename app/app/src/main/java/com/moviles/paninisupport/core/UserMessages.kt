package com.moviles.paninisupport.core

object UserMessages {

    object Auth {
        const val INVALID_CREDENTIALS = "Invalid email or password."
        const val LOGIN_EMPTY_FIELDS = "Please enter your email and password."
        const val LOGIN_FAILED = "Login failed. Please try again."
        const val LOGIN_FOOTER = "Panini Support — FIFA World Cup 2026"
    }

    object Network {
        const val COULD_NOT_CONNECT = "Could not connect to the server. Please try again."
    }

    object Tickets {
        const val LOAD_ERROR = "Could not load tickets. Please try again."
        const val CREATE_EMPTY_FIELDS = "Title, description, and provider are required."
        const val CREATE_SUCCESS = "Ticket created successfully."
        const val STATUS_UPDATE_SUCCESS = "Status updated successfully."
        const val PRIORITY_UPDATE_SUCCESS = "Priority updated successfully."
        const val NOT_FOUND = "Ticket not found."
    }

    object Screens {
        const val LOGIN_TITLE = "Login"
        const val TICKET_LIST_TITLE = "Support Tickets"
        const val TICKET_DETAIL_TITLE = "Ticket Detail"
        const val CREATE_TICKET_TITLE = "New Ticket"
    }

    object Accessibility {
        const val CREATE_TICKET = "Create new ticket"
        const val BACK = "Go back"
        const val LOGOUT = "Logout"
    }
}
