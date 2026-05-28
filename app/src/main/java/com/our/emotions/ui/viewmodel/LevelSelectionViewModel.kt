package com.our.emotions.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.our.emotions.data.repository.LevelRepository
import com.our.emotions.data.repository.LevelProgressStore
import com.our.emotions.domain.model.EmotionLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LevelSelectionViewModel(
    private val repository: LevelRepository = LevelRepository(),
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        LevelSelectionUiState(
            levels = emptyList(),
            progress = LevelProgressUiState.empty()
        )
    )
    val uiState: StateFlow<LevelSelectionUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        val completedCount = LevelProgressStore.completedCount()
        val levels = repository.loadLevels(completedCount = completedCount)
        _uiState.value = LevelSelectionUiState(
            levels = levels,
            progress = LevelProgressUiState(
                displayName = "Explorador",
                avatarEmoji = "👦",
                discoveredCount = completedCount,
                totalCount = levels.size,
                encouragement = "\"Continue assim! Você está indo muito bem!\""
            )
        )
    }
}

data class LevelSelectionUiState(
    val levels: List<EmotionLevel>,
    val progress: LevelProgressUiState,
)

data class LevelProgressUiState(
    val displayName: String,
    val avatarEmoji: String,
    val discoveredCount: Int,
    val totalCount: Int,
    val encouragement: String,
) {
    companion object {
        fun empty() = LevelProgressUiState(
            displayName = "",
            avatarEmoji = "👦",
            discoveredCount = 0,
            totalCount = 0,
            encouragement = ""
        )
    }
}
