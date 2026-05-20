package com.abdessamad.orbyt.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val domain: GoalDomain = GoalDomain.PERSO,
    val targetDate: Long? = null,
    val status: GoalStatus = GoalStatus.IN_PROGRESS,
    val isArchived: Boolean = false
)

enum class GoalDomain { ETUDES, TRAVAIL, SPORT, PERSO }
enum class GoalStatus { IN_PROGRESS, ACHIEVED, ABANDONED }