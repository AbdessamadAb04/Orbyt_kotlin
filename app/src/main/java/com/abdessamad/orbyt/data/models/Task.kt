package com.abdessamad.orbyt.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

enum class TaskType {
    SINGLE,
    RECURRING
}

enum class TaskPriority {
    LOW,
    MEDIUM,
    HIGH
}

enum class TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String?,
    val type: TaskType,
    val priority: TaskPriority,
    val status: TaskStatus,
    val dueDate: Date?,
    val nebulaId: UUID?,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)