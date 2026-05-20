package com.abdessamad.orbyt.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val folderId: UUID?,
    val title: String?,
    val content: String,
    val colorHex: String,
    val isPinned: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

@Entity(tableName = "note_folders")
data class NoteFolder(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val colorHex: String,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)