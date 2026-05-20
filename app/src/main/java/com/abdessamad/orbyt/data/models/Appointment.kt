package com.abdessamad.orbyt.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val taskId: UUID,
    val dateTime: Date,
    val location: String?,
    val reminderMins: Int?,
    val isDday: Boolean = false,
    val createdAt: Date = Date()
)