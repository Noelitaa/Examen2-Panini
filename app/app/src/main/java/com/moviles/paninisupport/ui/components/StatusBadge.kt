package com.moviles.paninisupport.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.paninisupport.data.remote.model.TicketStatus
import com.moviles.paninisupport.ui.theme.StatusClosed
import com.moviles.paninisupport.ui.theme.StatusInProgress
import com.moviles.paninisupport.ui.theme.StatusOpen
import com.moviles.paninisupport.ui.theme.StatusResolved

@Composable
fun StatusBadge(status: String) {
    val color = when (status) {
        TicketStatus.OPEN -> StatusOpen
        TicketStatus.IN_PROGRESS -> StatusInProgress
        TicketStatus.RESOLVED -> StatusResolved
        TicketStatus.CLOSED -> StatusClosed
        else -> Color.Gray
    }
    val label = when (status) {
        TicketStatus.IN_PROGRESS -> "IN PROGRESS"
        else -> status
    }
    Text(
        text = label,
        color = color,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .border(width = 1.dp, color = color, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}
