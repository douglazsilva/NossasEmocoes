package com.our.emotions.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.our.emotions.ui.theme.BluePrimary
import com.our.emotions.ui.theme.TextSecondaryLight
import com.our.emotions.ui.viewmodel.BadgeStatus
import com.our.emotions.ui.viewmodel.ProgressSnapshotUiState

@Composable
fun ProgressSnapshotCard(
    snapshot: ProgressSnapshotUiState,
    onViewAllBadges: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SEU PROGRESSO",
                style = MaterialTheme.typography.labelLarge,
                color = TextSecondaryLight
            )
            Text(
                text = "VER TODAS AS MEDALHAS",
                style = MaterialTheme.typography.labelLarge,
                color = BluePrimary,
                modifier = Modifier.clickable { onViewAllBadges() }
            )
        }
        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = CardDefaults.outlinedCardBorder(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "${snapshot.discoveredCount}/${snapshot.totalCount}",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Black,
                            color = BluePrimary
                        )
                        Text(
                            text = "Emoções descobertas",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextSecondaryLight
                        )
                    }
                    Row {
                        snapshot.badgePreview.forEach { badge ->
                            val bubbleColor = if (badge.status == BadgeStatus.Earned) badge.tint else Color(0xFFE2E8F0)
                            val iconTint = if (badge.status == BadgeStatus.Earned) Color.White else TextSecondaryLight
                            BadgeBubble(
                                color = bubbleColor,
                                icon = badge.icon,
                                contentDescription = badge.title,
                                iconTint = iconTint
                            )
                        }
                        if (snapshot.extraBadgeCount > 0) {
                            BadgeBubble(
                                color = Color(0xFFE2E8F0),
                                text = "+${snapshot.extraBadgeCount}",
                                textColor = TextSecondaryLight
                            )
                        }
                    }
                }
                AppProgressBar(
                    progress = snapshot.progress.coerceIn(0f, 1f),
                    height = 10.dp
                )
            }
        }
    }
}

@Composable
private fun BadgeBubble(
    color: Color,
    text: String? = null,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    contentDescription: String? = null,
    textColor: Color = Color.White,
    iconTint: Color = Color.White,
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .offset(x = (-8).dp)
            .clip(CircleShape)
            .background(color)
            .shadow(4.dp, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconTint,
            )
        } else {
            Text(text = text.orEmpty(), fontSize = 12.sp, color = textColor, textAlign = TextAlign.Center)
        }
    }
}
