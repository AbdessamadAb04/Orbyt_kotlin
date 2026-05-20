package com.abdessamad.orbyt.data.local.dao

import androidx.room.*
import com.abdessamad.orbyt.data.local.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE isArchived = 0 ORDER BY isPinned DESC, createdAt DESC")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE isArchived = 1 ORDER BY createdAt DESC")
    fun getArchivedNotes(): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE isPinned = 1 AND isArchived = 0 LIMIT 1")
    fun getPinnedNote(): Flow<Note?>

    @Query("SELECT * FROM notes WHERE content LIKE '%' || :query || '%' AND isArchived = 0")
    fun searchNotes(query: String): Flow<List<Note>>

    @Query("DELETE FROM notes WHERE isArchived = 1")
    suspend fun clearArchivedNotes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}