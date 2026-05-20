package com.abdessamad.orbyt.data.dao

import androidx.room.*
import com.abdessamad.orbyt.data.models.Note
import com.abdessamad.orbyt.data.models.NoteFolder
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: UUID): Flow<Note>

    @Query("SELECT * FROM notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteFolder(folder: NoteFolder)

    @Update
    suspend fun updateNoteFolder(folder: NoteFolder)

    @Delete
    suspend fun deleteNoteFolder(folder: NoteFolder)

    @Query("SELECT * FROM note_folders WHERE id = :id")
    fun getNoteFolderById(id: UUID): Flow<NoteFolder>

    @Query("SELECT * FROM note_folders ORDER BY updatedAt DESC")
    fun getAllNoteFolders(): Flow<List<NoteFolder>>
}