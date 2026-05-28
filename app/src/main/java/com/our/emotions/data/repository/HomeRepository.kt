package com.our.emotions.data.repository

import com.our.emotions.domain.model.HomeContent
import com.our.emotions.domain.model.StatItem
import com.our.emotions.ui.theme.AccentAmber
import com.our.emotions.ui.theme.AccentBlue

class HomeRepository {
    fun loadHomeContent(): HomeContent {
        return HomeContent(
            welcomeTitle = "Oi, Explorador!",
            welcomeSubtitle = "Pronto para descobrir novos sentimentos hoje?",
            stats = listOf(
                StatItem(title = "5 dias", label = "Sequência atual", tint = AccentAmber),
                StatItem(title = "1.240", label = "Pontos totais", tint = AccentBlue),
            )
        )
    }
}
