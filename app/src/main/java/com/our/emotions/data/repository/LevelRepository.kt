package com.our.emotions.data.repository

import com.our.emotions.domain.model.EmotionLevel
import com.our.emotions.domain.model.LevelStatus
import com.our.emotions.ui.theme.AccentAmber
import com.our.emotions.ui.theme.AccentBlue
import com.our.emotions.ui.theme.AccentPurple
import com.our.emotions.ui.theme.AccentRed
import com.our.emotions.ui.theme.TextSecondaryLight
import androidx.compose.ui.graphics.Color

class LevelRepository {
    fun loadLevels(completedCount: Int): List<EmotionLevel> {
        val baseLevels = listOf(
            EmotionLevel(1, "Feliz", "\uD83D\uDE0A", AccentAmber, AccentAmber.copy(alpha = 0.15f), LevelStatus.Locked, 0),
            EmotionLevel(2, "Triste", "\uD83D\uDE22", AccentBlue, AccentBlue.copy(alpha = 0.12f), LevelStatus.Locked, 0),
            EmotionLevel(3, "Bravo", "\uD83D\uDE21", AccentRed, AccentRed.copy(alpha = 0.12f), LevelStatus.Locked, 0),
            EmotionLevel(4, "Surpreso", "\uD83D\uDE32", AccentPurple, AccentPurple.copy(alpha = 0.12f), LevelStatus.Locked, 0),
            EmotionLevel(5, "Nojo", "\uD83E\uDD22", TextSecondaryLight, Color(0xFFE5E7EB), LevelStatus.Locked, 0),
            EmotionLevel(6, "Com medo", "\uD83D\uDE31", TextSecondaryLight, Color(0xFFE5E7EB), LevelStatus.Locked, 0),
        )

        return baseLevels.mapIndexed { index, level ->
            when {
                index < completedCount -> level.copy(status = LevelStatus.Completed, stars = 3)
                index == completedCount -> level.copy(status = LevelStatus.PlayNow, stars = 0)
                else -> level.copy(status = LevelStatus.Locked, stars = 0)
            }
        }
    }
}
