package com.our.emotions.domain.model

import androidx.compose.ui.graphics.Color

data class EmotionLevel(
    val level: Int,
    val name: String,
    val emoji: String,
    val tint: Color,
    val bg: Color,
    val status: LevelStatus,
    val stars: Int,
)

enum class LevelStatus {
    Completed,
    TryAgain,
    PlayNow,
    NextLevel,
    Locked,
}
