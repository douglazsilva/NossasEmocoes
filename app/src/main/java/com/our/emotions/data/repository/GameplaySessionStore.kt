package com.our.emotions.data.repository

import com.our.emotions.domain.model.EmotionOption
import com.our.emotions.domain.model.GameplayQuestion

object GameplaySessionStore {
    private val repository = GameplayRepository()
    private var selectedLevel: Int = 1
    private var questions: List<GameplayQuestion> = repository.loadQuestions(level = selectedLevel)
    private var currentIndex: Int = 0
    private var lastAnswerCorrect: Boolean = true
    private var lastSelectedLabel: String? = null

    fun currentQuestion(): GameplayQuestion = questions[currentIndex]

    fun selectedLevel(): Int = selectedLevel

    fun selectedLevelLabel(): String = repository.levelLabel(selectedLevel)

    fun currentIndex(): Int = currentIndex

    fun totalQuestions(): Int = questions.size

    fun isAnswerCorrect(answerLabel: String): Boolean {
        return answerLabel == currentQuestion().correctLabel
    }

    fun registerAnswer(answerLabel: String): Boolean {
        val correct = isAnswerCorrect(answerLabel)
        lastAnswerCorrect = correct
        lastSelectedLabel = answerLabel
        return correct
    }

    fun lastAnswerCorrect(): Boolean = lastAnswerCorrect

    fun lastSelectedLabel(): String? = lastSelectedLabel

    fun correctOption(): EmotionOption? {
        val question = currentQuestion()
        return question.options.firstOrNull { it.label == question.correctLabel }
    }

    fun hasNextQuestion(): Boolean = currentIndex < questions.lastIndex

    fun advanceQuestion(): Boolean {
        return if (hasNextQuestion()) {
            currentIndex += 1
            true
        } else {
            false
        }
    }

    fun startLevel(level: Int) {
        selectedLevel = level
        reset()
    }

    fun reset() {
        questions = repository.loadQuestions(level = selectedLevel)
        currentIndex = 0
        lastAnswerCorrect = true
        lastSelectedLabel = null
    }
}
