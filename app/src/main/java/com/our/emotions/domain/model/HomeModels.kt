package com.our.emotions.domain.model

import androidx.compose.ui.graphics.Color

data class StatItem(
    val title: String,
    val label: String,
    val tint: Color,
)

data class HomeContent(
    val welcomeTitle: String,
    val welcomeSubtitle: String,
    val stats: List<StatItem>,
)
