package com.abdessamad.orbyt.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class Project(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val colorTag: String = "#1A56DB",
    val deadline: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)