package com.abdessamad.orbyt.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_logs")
data class HabitLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val habitId: Long,      // foreign key to Habit
    val date: String,       // stored as "YYYY-MM-DD" string
    val isDone: Boolean = false
)