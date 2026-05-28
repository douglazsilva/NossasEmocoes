package com.our.emotions.domain.model

import androidx.annotation.DrawableRes

data class EmotionOption(
    val label: String,
    @DrawableRes val imageResId: Int,
)

data class GameplayQuestion(
    val prompt: String,
    val options: List<EmotionOption>,
    val correctLabel: String,
    val explanation: String,
)
