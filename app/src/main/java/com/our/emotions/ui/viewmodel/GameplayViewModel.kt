package com.our.emotions.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.our.emotions.data.repository.GameplaySessionStore
import com.our.emotions.domain.model.EmotionOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameplayViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        GameplayUiState.empty()
    )
    val uiState: StateFlow<GameplayUiState> = _uiState.asStateFlow()

    init {
        loadQuestion()
    }

    fun onOptionSelected(option: EmotionOption): Boolean {
        return GameplaySessionStore.registerAnswer(option.label)
    }

    fun loadQuestion() {
        val question = GameplaySessionStore.currentQuestion()
        val index = GameplaySessionStore.currentIndex()
        val total = GameplaySessionStore.totalQuestions()
        _uiState.value = GameplayUiState(
            levelLabel = GameplaySessionStore.selectedLevelLabel(),
            prompt = question.prompt,
            options = question.options,
            questionNumber = index + 1,
            totalQuestions = total
        )
    }
}

data class GameplayUiState(
    val levelLabel: String,
    val prompt: String,
    val options: List<EmotionOption>,
    val questionNumber: Int,
    val totalQuestions: Int,
) {
    companion object {
        fun empty() = GameplayUiState(
            levelLabel = "NÍVEL 1",
            prompt = "",
            options = emptyList(),
            questionNumber = 1,
            totalQuestions = 1
        )
    }
}
