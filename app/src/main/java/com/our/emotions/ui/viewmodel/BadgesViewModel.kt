package com.our.emotions.ui.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoGraph
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Flag
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.MilitaryTech
import androidx.compose.material.icons.rounded.RocketLaunch
import androidx.lifecycle.ViewModel
import com.our.emotions.data.repository.LevelProgressStore
import com.our.emotions.ui.theme.AccentAmber
import com.our.emotions.ui.theme.AccentBlue
import com.our.emotions.ui.theme.AccentPurple
import com.our.emotions.ui.theme.AccentRed
import com.our.emotions.ui.theme.BluePrimary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BadgesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BadgesUiState.empty())
    val uiState: StateFlow<BadgesUiState> = _uiState.asStateFlow()

    fun refresh() {
        val completedSessions = LevelProgressStore.completedCount()
        val badges = buildSessionBadges(completedSessions)
        val collected = badges.count { it.status == BadgeStatus.Earned }
        _uiState.value = BadgesUiState(
            collected = collected,
            total = badges.size,
            badges = badges
        )
    }
}

enum class BadgeStatus {
    Earned,
    InProgress,
    Locked
}

data class BadgeUiModel(
    val title: String,
    val subtitle: String? = null,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val tint: androidx.compose.ui.graphics.Color,
    val status: BadgeStatus,
    val progressLabel: String? = null,
    val progress: Float = 0f,
)

data class BadgesUiState(
    val collected: Int,
    val total: Int,
    val badges: List<BadgeUiModel>,
) {
    companion object {
        fun empty() = BadgesUiState(
            collected = 0,
            total = 0,
            badges = emptyList()
        )
    }
}

private data class BadgeDefinition(
    val title: String,
    val subtitle: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val tint: androidx.compose.ui.graphics.Color,
    val threshold: Int,
)

internal fun buildSessionBadges(completedSessions: Int): List<BadgeUiModel> {
    val definitions = listOf(
        BadgeDefinition(
            title = "Primeira sessão",
            subtitle = "Conclua 1 sessão",
            icon = Icons.Rounded.Flag,
            tint = AccentAmber,
            threshold = 1
        ),
        BadgeDefinition(
            title = "Trinca focada",
            subtitle = "Conclua 3 sessões",
            icon = Icons.Rounded.EmojiEvents,
            tint = AccentBlue,
            threshold = 3
        ),
        BadgeDefinition(
            title = "Cinco jornadas",
            subtitle = "Conclua 5 sessões",
            icon = Icons.Rounded.AutoGraph,
            tint = BluePrimary,
            threshold = 5
        ),
        BadgeDefinition(
            title = "Ritmo de 10",
            subtitle = "Conclua 10 sessões",
            icon = Icons.Rounded.LocalFireDepartment,
            tint = AccentRed,
            threshold = 10
        ),
        BadgeDefinition(
            title = "Vinte sessões",
            subtitle = "Conclua 20 sessões",
            icon = Icons.Rounded.MilitaryTech,
            tint = AccentPurple,
            threshold = 20
        ),
        BadgeDefinition(
            title = "Maratona 30",
            subtitle = "Conclua 30 sessões",
            icon = Icons.Rounded.RocketLaunch,
            tint = AccentAmber,
            threshold = 30
        ),
    )

    val nextIndex = definitions.indexOfFirst { completedSessions < it.threshold }
    return definitions.mapIndexed { index, definition ->
        val status = when {
            completedSessions >= definition.threshold -> BadgeStatus.Earned
            index == nextIndex -> BadgeStatus.InProgress
            else -> BadgeStatus.Locked
        }

        val progress = if (status == BadgeStatus.InProgress) {
            (completedSessions.toFloat() / definition.threshold.toFloat()).coerceIn(0f, 1f)
        } else {
            0f
        }

        val progressLabel = if (status == BadgeStatus.InProgress) {
            "${completedSessions}/${definition.threshold}"
        } else {
            null
        }

        BadgeUiModel(
            title = definition.title,
            subtitle = definition.subtitle,
            icon = definition.icon,
            tint = definition.tint,
            status = status,
            progressLabel = progressLabel,
            progress = progress
        )
    }
}
