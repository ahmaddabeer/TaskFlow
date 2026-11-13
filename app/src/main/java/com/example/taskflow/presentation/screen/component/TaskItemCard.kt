package com.example.taskflow.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskflow.data.room.enums.StatusEnum



private val Dark = Color(0xFF202A24)
private val Grey = Color(0xFF858585)


@Composable
 fun TaskItemCard(
    title: String,
    subtitle: String,
    status: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text(title, color = Dark)
                Spacer(Modifier.height(6.dp))
                Text(subtitle, color = Grey, fontSize = 12.sp)
            }

            Text(
                status,
                color = if (status == StatusEnum.PENDING.toString()) Color.Red else if (status == StatusEnum.IN_PROGRESS.toString()) Color.Yellow else Color.Green,
                fontSize = 11.sp
            )
        }
    }
}
