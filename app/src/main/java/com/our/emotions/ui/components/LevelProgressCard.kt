package com.our.emotions.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.our.emotions.ui.theme.BluePrimary
import com.our.emotions.ui.theme.TextSecondaryLight

@Composable
fun LevelProgressCard(
    modifier: Modifier = Modifier,
    displayName: String,
    avatarEmoji: String,
    discoveredCount: Int,
    totalCount: Int,
    encouragement: String,
) {
    val progress = if (totalCount > 0) discoveredCount.toFloat() / totalCount else 0f
    val progressLabel = "${(progress * 100).toInt()}%"

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(BluePrimary.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = avatarEmoji, fontSize = 28.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Oi, $displayName!",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Você descobriu $discoveredCount de $totalCount emoções",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "PROGRESSO",
                    style = MaterialTheme.typography.labelLarge,
                    color = BluePrimary
                )
                Text(
                    text = progressLabel,
                    style = MaterialTheme.typography.labelLarge,
                    color = BluePrimary
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            AppProgressBar(progress = progress)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = encouragement,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondaryLight
            )
        }
    }
}
