package com.moviles.paninisupport.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviles.paninisupport.data.remote.model.TicketDto
import com.moviles.paninisupport.ui.theme.PaniniTextSecondary

@Composable
fun TicketCard(
    ticket: TicketDto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PriorityBadge(priority = ticket.priority)
                Spacer(modifier = Modifier.width(8.dp))
                StatusBadge(status = ticket.status)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = ticket.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = ticket.provider,
                fontSize = 13.sp,
                color = PaniniTextSecondary
            )
            Text(
                text = ticket.category,
                fontSize = 12.sp,
                color = PaniniTextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = ticket.createdAt.take(10),
                fontSize = 11.sp,
                color = PaniniTextSecondary
            )
        }
    }
}
