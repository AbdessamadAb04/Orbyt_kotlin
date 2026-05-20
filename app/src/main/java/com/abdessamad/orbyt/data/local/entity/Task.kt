package com.abdessamad.orbyt.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,
    val priority: Priority = Priority.NORMAL,
    val status: Status = Status.TODO,
    val dueDate: Long? = null,       // stored as timestamp (milliseconds)
    val projectId: Long? = null,     // links to a Project (nullable = no project)
    val createdAt: Long = System.currentTimeMillis(),
    val isArchived: Boolean = false
)

enum class Priority { HIGH, NORMAL, LOW }
enum class Status { TODO, IN_PROGRESS, DONE }