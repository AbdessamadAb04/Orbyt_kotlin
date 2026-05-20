package com.abdessamad.orbyt.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

enum class NebulaStatus {
    ACTIVE,
    COMPLETED,
    ARCHIVED
}

@Entity(tableName = "nebulas")
data class Nebula(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String?,
    val colorHex: String,
    val targetDate: Date?,
    val status: NebulaStatus,
    val progress: Float = 0.0f,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)