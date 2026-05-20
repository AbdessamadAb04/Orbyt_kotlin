package com.abdessamad.orbyt.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val dateTime: Long,             // timestamp
    val location: String? = null,
    val reminderOffset: Long? = null, // minutes before event
    val isDDay: Boolean = false,
    val isArchived: Boolean = false
)
