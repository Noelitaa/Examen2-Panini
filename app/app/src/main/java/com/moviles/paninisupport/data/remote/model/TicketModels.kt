package com.moviles.paninisupport.data.remote.model

data class TicketDto(
    val id: String,
    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val provider: String,
    val category: String,
    val createdAt: String
)

data class CreateTicketRequest(
    val title: String,
    val description: String,
    val priority: String,
    val provider: String,
    val category: String
)

data class UpdateStatusRequest(
    val status: String
)

data class UpdatePriorityRequest(
    val priority: String
)

object TicketPriority {
    const val CRITICAL = "CRITICAL"
    const val HIGH = "HIGH"
    const val MEDIUM = "MEDIUM"
    const val LOW = "LOW"

    val all = listOf(CRITICAL, HIGH, MEDIUM, LOW)

    val sortOrder = mapOf(CRITICAL to 0, HIGH to 1, MEDIUM to 2, LOW to 3)
}

object TicketStatus {
    const val OPEN = "OPEN"
    const val IN_PROGRESS = "IN_PROGRESS"
    const val RESOLVED = "RESOLVED"
    const val CLOSED = "CLOSED"

    val all = listOf(OPEN, IN_PROGRESS, RESOLVED, CLOSED)
}

object TicketCategory {
    const val DISTRIBUTION = "DISTRIBUTION"
    const val INVENTORY = "INVENTORY"
    const val LOGISTICS = "LOGISTICS"
    const val QUALITY = "QUALITY"
    const val PROVIDER = "PROVIDER"

    val all = listOf(DISTRIBUTION, INVENTORY, LOGISTICS, QUALITY, PROVIDER)
}
