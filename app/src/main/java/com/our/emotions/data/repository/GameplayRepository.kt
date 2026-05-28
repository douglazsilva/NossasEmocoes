package com.our.emotions.data.repository

import com.our.emotions.R
import com.our.emotions.data.catalog.EmotionImageCatalog
import com.our.emotions.domain.model.EmotionOption
import com.our.emotions.domain.model.GameplayQuestion

class GameplayRepository {
    fun loadQuestions(level: Int): List<GameplayQuestion> {
        val correctLabel = emotionForLevel(level)
        return List(5) {
            GameplayQuestion(
                prompt = promptFor(correctLabel),
                options = buildRandomizedOptions(correctLabel = correctLabel),
                correctLabel = correctLabel,
                explanation = explanationFor(correctLabel)
            )
        }
    }

    fun emotionForLevel(level: Int): String {
        return when (level) {
            1 -> "Feliz"
            2 -> "Triste"
            3 -> "Bravo"
            4 -> "Surpreso"
            5 -> "Nojo"
            6 -> "Com medo"
            else -> "Feliz"
        }
    }

    fun levelLabel(level: Int): String = "NÍVEL $level"

    private fun buildRandomizedOptions(correctLabel: String): List<EmotionOption> {
        val correctOption = optionFor(
            label = correctLabel,
            fallbackImageResId = fallbackFor(correctLabel)
        )
        val distractorOptions = supportedLabels()
            .filterNot { it == correctLabel }
            .shuffled()
            .take(3)
            .map { label ->
                optionFor(label = label, fallbackImageResId = fallbackFor(label))
            }

        return (distractorOptions + correctOption).shuffled()
    }

    private fun supportedLabels(): List<String> = listOf(
        "Feliz",
        "Triste",
        "Surpreso",
        "Bravo",
        "Nojo",
        "Com medo",
    )

    private fun promptFor(label: String): String {
        return when (label) {
            "Feliz" -> "feliz"
            "Triste" -> "triste"
            "Surpreso" -> "surpreso"
            "Bravo" -> "bravo"
            "Nojo" -> "com nojo"
            "Com medo" -> "com medo"
            else -> label.lowercase()
        }
    }

    private fun explanationFor(label: String): String {
        return when (label) {
            "Feliz" -> "Sorrisos e olhos relaxados costumam indicar felicidade."
            "Triste" -> "Olhar caído e expressão sem sorriso costumam indicar tristeza."
            "Surpreso" -> "Observe os olhos bem abertos, a sobrancelha levantada e a boca aberta."
            "Bravo" -> "Sobrancelhas franzidas e lábios tensos são sinais comuns de raiva."
            "Nojo" -> "Nariz enrugado e lábios contraídos costumam indicar nojo."
            "Com medo" -> "Olhos bem abertos e expressão tensa podem indicar medo."
            else -> "Observe com calma a expressão do rosto antes de responder."
        }
    }

    private fun optionFor(
        label: String,
        fallbackImageResId: Int,
    ): EmotionOption {
        val imageResId = EmotionImageCatalog.randomFor(label)
            ?: EmotionImageCatalog.allFor(label).firstOrNull()
            ?: fallbackImageResId
        return EmotionOption(label = label, imageResId = imageResId)
    }

    private fun fallbackFor(label: String): Int {
        return when (label) {
            "Feliz" -> R.drawable.emo_feliz_1
            "Triste" -> R.drawable.emo_triste_1
            "Surpreso" -> R.drawable.emo_surpreso_1
            "Bravo" -> R.drawable.emo_raiva_1
            "Nojo" -> R.drawable.emo_nojo_1
            "Com medo" -> R.drawable.emo_medo_1
            else -> R.drawable.emo_feliz_1
        }
    }
}
