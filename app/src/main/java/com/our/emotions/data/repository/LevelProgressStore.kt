package com.our.emotions.data.repository

object LevelProgressStore {
    private var completedLevels: Int = 0

    fun completedCount(): Int = completedLevels

    fun markLevelCompleted() {
        completedLevels += 1
    }

    fun reset() {
        completedLevels = 0
    }
}
