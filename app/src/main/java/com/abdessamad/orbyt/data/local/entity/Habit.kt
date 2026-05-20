package com.abdessamad.orbyt.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val emoji: String = "✅",
    val isArchived: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)