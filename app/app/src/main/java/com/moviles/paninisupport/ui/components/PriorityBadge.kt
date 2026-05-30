package com.moviles.paninisupport.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.paninisupport.data.remote.model.TicketPriority
import com.moviles.paninisupport.ui.theme.PriorityCritical
import com.moviles.paninisupport.ui.theme.PriorityHigh
import com.moviles.paninisupport.ui.theme.PriorityLow
import com.moviles.paninisupport.ui.theme.PriorityMedium

@Composable
fun PriorityBadge(priority: String) {
    val color = when (priority) {
        TicketPriority.CRITICAL -> PriorityCritical
        TicketPriority.HIGH -> PriorityHigh
        TicketPriority.MEDIUM -> PriorityMedium
        TicketPriority.LOW -> PriorityLow
        else -> Color.Gray
    }
    Text(
        text = priority,
        color = Color.White,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .background(color = color, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}
