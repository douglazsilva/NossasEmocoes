package com.our.emotions.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.our.emotions.data.repository.FeedbackResult
import com.our.emotions.ui.components.PrimaryActionButton
import com.our.emotions.ui.navigation.AppScreen
import com.our.emotions.ui.theme.AccentAmber
import com.our.emotions.ui.theme.AccentBlue
import com.our.emotions.ui.theme.AccentGreen
import com.our.emotions.ui.theme.AccentRed
import com.our.emotions.ui.theme.BluePrimary
import com.our.emotions.ui.theme.EmotionTheme
import com.our.emotions.ui.theme.TextSecondaryLight
import com.our.emotions.ui.viewmodel.FeedbackUiState
import com.our.emotions.ui.viewmodel.FeedbackViewModel

@Composable
fun FeedbackRoute(
    onNavigate: (AppScreen) -> Unit,
    viewModel: FeedbackViewModel = viewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.refresh()
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    FeedbackScreen(
        uiState = uiState,
        onNext = {
            val hasNext = viewModel.onNextQuestion()
            if (uiState.isCorrect) {
                onNavigate(if (hasNext) AppScreen.Gameplay else AppScreen.LevelSelection)
            } else {
                onNavigate(AppScreen.Gameplay)
            }
        },
    )
}

@Composable
fun FeedbackScreen(
    uiState: FeedbackUiState,
    onNext: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            DecorativeConfetti()
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SuccessHeader(result = uiState.result, isCorrect = uiState.isCorrect)
                Spacer(modifier = Modifier.height(20.dp))
                SuccessCard(result = uiState.result, isCorrect = uiState.isCorrect)
                Spacer(modifier = Modifier.height(24.dp))
                PrimaryActionButton(
                    text = when {
                        !uiState.isCorrect -> "Tentar novamente"
                        uiState.isLastQuestion -> "Finalizar"
                        else -> "Próxima pergunta"
                    },
                    onClick = onNext,
                    trailingIcon = Icons.AutoMirrored.Rounded.ArrowForward
                )
                TextButton(onClick = {}) {
                    Text(text = "Revisar esta emoção", color = TextSecondaryLight)
                }
            }
        }
    }
}

@Composable
private fun SuccessHeader(result: FeedbackResult, isCorrect: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(84.dp)
                .clip(CircleShape)
                .background(BluePrimary.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isCorrect) Icons.Rounded.CheckCircle else Icons.Rounded.Info,
                contentDescription = null,
                tint = if (isCorrect) BluePrimary else AccentRed,
                modifier = Modifier.size(48.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = result.title,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = result.description,
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondaryLight,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
private fun SuccessCard(result: FeedbackResult, isCorrect: Boolean) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(MaterialTheme.shapes.medium)
                    .background(result.gradient),
                contentAlignment = Alignment.TopEnd
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(if (isCorrect) BluePrimary else AccentRed)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (isCorrect) "CORRETO" else "INCORRETO",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "EMOÇÃO IDENTIFICADA",
                    style = MaterialTheme.typography.labelLarge,
                    color = TextSecondaryLight
                )
                Text(
                    text = result.emotionName,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Info,
                        contentDescription = null,
                        tint = TextSecondaryLight,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = result.explanation,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                    Text(
                        text = "Progresso do nível",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight
                    )
                    Text(
                        text = "${result.levelName} \u2022 ${result.levelProgressLabel}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondaryLight
                    )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { result.progressValue },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = BluePrimary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun DecorativeConfetti() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.TopStart)
                .offset(x = 16.dp, y = 24.dp)
                .background(AccentAmber.copy(alpha = 0.2f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-24).dp, y = 48.dp)
                .background(AccentBlue.copy(alpha = 0.2f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.BottomStart)
                .offset(x = 12.dp, y = (-32).dp)
                .background(AccentGreen.copy(alpha = 0.2f), CircleShape)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedbackScreenPreview() {
    EmotionTheme {
        FeedbackScreen(
            uiState = FeedbackUiState(
                result = FeedbackResult(
                    title = "Muito bem!",
                    description = "Você identificou a emoção corretamente! Está melhorando a cada dia.",
                    emotionName = "Feliz",
                    explanation = "Você acertou ao reconhecer o sorriso e os olhos brilhantes.",
                    levelName = "Nível 1",
                    levelProgressLabel = "Nível 5 de 10",
                    progressValue = 0.5f,
                    gradient = Brush.linearGradient(
                        listOf(AccentAmber.copy(alpha = 0.45f), AccentBlue.copy(alpha = 0.2f))
                    ),
                ),
                isLastQuestion = false,
                isCorrect = true
            ),
            onNext = {}
        )
    }
}
