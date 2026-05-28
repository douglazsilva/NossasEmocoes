package com.our.emotions.data.repository

import com.our.emotions.ui.theme.AccentAmber
import com.our.emotions.ui.theme.AccentBlue
import androidx.compose.ui.graphics.Brush

class FeedbackRepository {
    fun loadResult(): FeedbackResult {
        return FeedbackResult(
            title = "Muito bem!",
            description = "Você identificou a emoção corretamente! Está melhorando a cada dia.",
            emotionName = "Feliz",
            explanation = "Você acertou ao reconhecer o sorriso e os olhos brilhantes.",
            levelName = "Nível 1",
            levelProgressLabel = "Nível 5 de 10",
            progressValue = 0.5f,
            gradient = Brush.linearGradient(
                listOf(AccentAmber.copy(alpha = 0.45f), AccentBlue.copy(alpha = 0.2f))
            )
        )
    }
}

data class FeedbackResult(
    val title: String,
    val description: String,
    val emotionName: String,
    val explanation: String,
    val levelName: String,
    val levelProgressLabel: String,
    val progressValue: Float,
    val gradient: Brush,
)
