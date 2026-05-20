package com.abdessamad.orbyt.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val taskId: UUID,
    val targetDays: Int,
    val colorHex: String,
    val createdAt: Date = Date()
)

@Entity(tableName = "habit_logs")
data class HabitLog(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val habitId: UUID,
    val logDate: Date,
    val isDone: Boolean = false,
    val createdAt: Date = Date()
)