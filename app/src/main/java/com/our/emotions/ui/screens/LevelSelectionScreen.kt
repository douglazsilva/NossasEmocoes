package com.our.emotions.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.our.emotions.data.repository.GameplaySessionStore
import com.our.emotions.domain.model.EmotionLevel
import com.our.emotions.domain.model.LevelStatus
import com.our.emotions.ui.components.AppTopBar
import com.our.emotions.ui.components.LevelProgressCard
import com.our.emotions.ui.navigation.AppScreen
import com.our.emotions.ui.theme.AccentAmber
import com.our.emotions.ui.theme.AccentBlue
import com.our.emotions.ui.theme.BluePrimary
import com.our.emotions.ui.theme.EmotionTheme
import com.our.emotions.ui.theme.TextSecondaryLight
import com.our.emotions.ui.viewmodel.LevelSelectionUiState
import com.our.emotions.ui.viewmodel.LevelSelectionViewModel
import com.our.emotions.ui.viewmodel.LevelProgressUiState

@Composable
fun LevelSelectionRoute(
    onNavigate: (AppScreen) -> Unit,
    onBack: () -> Unit,
    viewModel: LevelSelectionViewModel = viewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LevelSelectionScreen(
        uiState = uiState,
        onNavigate = onNavigate,
        onBack = onBack,
    )
}

@Composable
fun LevelSelectionScreen(
    uiState: LevelSelectionUiState,
    onNavigate: (AppScreen) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { LevelSelectionTopBar(onBack = onBack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LevelProgressCard(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                displayName = uiState.progress.displayName,
                avatarEmoji = uiState.progress.avatarEmoji,
                discoveredCount = uiState.progress.discoveredCount,
                totalCount = uiState.progress.totalCount,
                encouragement = uiState.progress.encouragement
            )

            Text(
                text = "SELECIONE UM NÍVEL",
                style = MaterialTheme.typography.labelLarge,
                color = TextSecondaryLight,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp),
                textAlign = TextAlign.Center,
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.levels) { level ->
                    EmotionLevelCard(level = level, onPlay = {
                        if (level.status == LevelStatus.PlayNow) {
                            GameplaySessionStore.startLevel(level.level)
                            onNavigate(AppScreen.Gameplay)
                        }
                    })
                }
            }
        }
    }
}

@Composable
private fun LevelSelectionTopBar(
    onBack: () -> Unit,
) {
    AppTopBar(
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Explorador de Emoções",
                    style = MaterialTheme.typography.titleLarge,
                    color = BluePrimary
                )
                Text(
                    text = "MAPA DE DESCOBERTAS",
                    style = MaterialTheme.typography.labelLarge,
                    color = TextSecondaryLight
                )
            }
        },
        showNavigation = true,
        onNavigate = onBack,
        showSettings = false
    )
}

@Composable
private fun EmotionLevelCard(
    level: EmotionLevel,
    onPlay: () -> Unit,
) {
    val borderColor =
        if (level.status == LevelStatus.PlayNow) BluePrimary else level.tint.copy(alpha = 0.4f)
    val ring = if (level.status == LevelStatus.PlayNow) 2.dp else 1.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(enabled = level.status == LevelStatus.PlayNow) { onPlay() },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = level.bg),
        border = CardDefaults.outlinedCardBorder().copy(
            width = ring,
            brush = Brush.linearGradient(listOf(borderColor, borderColor))
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
                    .background(BluePrimary, CircleShape)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "NÍVEL ${level.level}",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = level.emoji, fontSize = 40.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = level.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (level.status == LevelStatus.Locked) TextSecondaryLight else level.tint
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    repeat(3) { index ->
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = null,
                            tint = if (index < level.stars) level.tint else TextSecondaryLight.copy(
                                alpha = 0.4f
                            ),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                StatusPill(level)
            }

            if (level.status == LevelStatus.Locked) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 0.4f))
                )
            }
        }
    }
}

@Composable
private fun StatusPill(level: EmotionLevel) {
    val (label, color, textColor) = when (level.status) {
        LevelStatus.Completed -> Triple("Concluído", level.tint, level.tint)
        LevelStatus.TryAgain -> Triple("Tente de novo", level.tint, level.tint)
        LevelStatus.PlayNow -> Triple("Jogar agora", BluePrimary, Color.White)
        LevelStatus.NextLevel -> Triple("Próximo nível", level.tint, level.tint)
        LevelStatus.Locked -> Triple("Bloqueado", TextSecondaryLight, TextSecondaryLight)
    }

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(color.copy(alpha = 0.2f))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = label.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LevelSelectionScreenPreview() {
    EmotionTheme {
        LevelSelectionScreen(
            uiState = LevelSelectionUiState(
                levels = listOf(
                    EmotionLevel(
                        1,
                        "Feliz",
                        "\uD83D\uDE0A",
                        AccentAmber,
                        AccentAmber.copy(alpha = 0.15f),
                        LevelStatus.Completed,
                        3
                    ),
                    EmotionLevel(
                        2,
                        "Triste",
                        "\uD83D\uDE22",
                        AccentBlue,
                        AccentBlue.copy(alpha = 0.12f),
                        LevelStatus.TryAgain,
                        1
                    ),
                ),
                progress = LevelProgressUiState(
                    displayName = "Explorador",
                    avatarEmoji = "👦",
                    discoveredCount = 1,
                    totalCount = 6,
                    encouragement = "\"Continue assim! Você está indo muito bem!\""
                )
            ),
            onNavigate = {},
            onBack = {}
        )
    }
}
