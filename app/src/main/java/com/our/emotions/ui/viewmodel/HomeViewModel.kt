package com.our.emotions.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.our.emotions.data.repository.HomeRepository
import com.our.emotions.data.repository.LevelProgressStore
import com.our.emotions.data.repository.LevelRepository
import com.our.emotions.domain.model.HomeContent
import com.our.emotions.domain.model.StatItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val repository: HomeRepository = HomeRepository(),
    private val levelRepository: LevelRepository = LevelRepository(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        HomeUiState(
            welcomeTitle = "",
            welcomeSubtitle = "",
            stats = emptyList(),
            progressSnapshot = ProgressSnapshotUiState.empty(),
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        val content = repository.loadHomeContent()
        _uiState.value = buildState(content)
    }

    private fun buildState(content: HomeContent): HomeUiState {
        val completedCount = LevelProgressStore.completedCount()
        val levels = levelRepository.loadLevels(completedCount = completedCount)
        val totalCount = levels.size
        val progress = if (totalCount > 0) {
            completedCount.toFloat() / totalCount.toFloat()
        } else {
            0f
        }
        val badges = buildSessionBadges(completedCount)
        val earned = badges.filter { it.status == BadgeStatus.Earned }
        val previewBadges = if (earned.isNotEmpty()) earned.take(2) else badges.take(2)
        val extraBadgeCount = (badges.size - previewBadges.size).coerceAtLeast(0)
        return HomeUiState(
            welcomeTitle = content.welcomeTitle,
            welcomeSubtitle = content.welcomeSubtitle,
            stats = content.stats,
            progressSnapshot = ProgressSnapshotUiState(
                discoveredCount = completedCount,
                totalCount = totalCount,
                progress = progress,
                badgePreview = previewBadges,
                extraBadgeCount = extraBadgeCount,
            ),
        )
    }
}

data class HomeUiState(
    val welcomeTitle: String,
    val welcomeSubtitle: String,
    val stats: List<StatItem>,
    val progressSnapshot: ProgressSnapshotUiState,
)

data class ProgressSnapshotUiState(
    val discoveredCount: Int,
    val totalCount: Int,
    val progress: Float,
    val badgePreview: List<BadgeUiModel>,
    val extraBadgeCount: Int,
) {
    companion object {
        fun empty() = ProgressSnapshotUiState(
            discoveredCount = 0,
            totalCount = 0,
            progress = 0f,
            badgePreview = emptyList(),
            extraBadgeCount = 0,
        )
    }
}
