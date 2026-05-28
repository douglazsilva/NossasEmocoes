package com.our.emotions.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Brush
import com.our.emotions.data.repository.FeedbackResult
import com.our.emotions.data.repository.GameplaySessionStore
import com.our.emotions.data.repository.LevelProgressStore
import com.our.emotions.ui.theme.AccentAmber
import com.our.emotions.ui.theme.AccentBlue
import com.our.emotions.ui.theme.AccentPurple
import com.our.emotions.ui.theme.AccentRed
import com.our.emotions.ui.theme.BluePrimary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FeedbackViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        FeedbackUiState(result = buildResult(), isLastQuestion = false, isCorrect = true)
    )
    val uiState: StateFlow<FeedbackUiState> = _uiState.asStateFlow()

    fun refresh() {
        val result = buildResult()
        val isLast = isLastQuestion()
        _uiState.value = FeedbackUiState(
            result = result,
            isLastQuestion = isLast,
            isCorrect = GameplaySessionStore.lastAnswerCorrect()
        )
    }

    fun onNextQuestion(): Boolean {
        val isCorrect = GameplaySessionStore.lastAnswerCorrect()
        if (!isCorrect) {
            return true
        }

        val hasNext = GameplaySessionStore.advanceQuestion()
        if (!hasNext) {
            LevelProgressStore.markLevelCompleted()
            GameplaySessionStore.reset()
        }
        val result = buildResult()
        val isLast = isLastQuestion()
        _uiState.value = FeedbackUiState(
            result = result,
            isLastQuestion = isLast,
            isCorrect = GameplaySessionStore.lastAnswerCorrect()
        )
        return hasNext
    }

    private fun buildResult(): FeedbackResult {
        val question = GameplaySessionStore.currentQuestion()
        val correctOption = GameplaySessionStore.correctOption()
        val index = GameplaySessionStore.currentIndex() + 1
        val total = GameplaySessionStore.totalQuestions()
        val progressValue = if (total > 0) index.toFloat() / total else 0f
        val tint = when (correctOption?.label) {
            "Feliz" -> AccentAmber
            "Triste" -> AccentBlue
            "Surpreso" -> AccentPurple
            "Bravo" -> AccentRed
            "Nojo" -> AccentRed
            "Com medo" -> AccentPurple
            else -> BluePrimary
        }
        val isCorrect = GameplaySessionStore.lastAnswerCorrect()

        val title = if (isCorrect) "Muito bem!" else "Tente novamente"
        val description = if (isCorrect) {
            "Você identificou a emoção corretamente! Está melhorando a cada dia."
        } else {
            "Essa ainda não é a emoção certa. Observe mais uma vez e tente de novo."
        }

        return FeedbackResult(
            title = title,
            description = description,
            emotionName = question.correctLabel,
            explanation = question.explanation,
            levelName = "Nível ${GameplaySessionStore.selectedLevel()}",
            levelProgressLabel = "Pergunta $index de $total",
            progressValue = progressValue,
            gradient = Brush.linearGradient(
                listOf(tint.copy(alpha = 0.45f), AccentBlue.copy(alpha = 0.2f))
            )
        )
    }

    private fun isLastQuestion(): Boolean {
        val index = GameplaySessionStore.currentIndex() + 1
        val total = GameplaySessionStore.totalQuestions()
        return index >= total
    }
}

data class FeedbackUiState(
    val result: FeedbackResult,
    val isLastQuestion: Boolean,
    val isCorrect: Boolean,
)
