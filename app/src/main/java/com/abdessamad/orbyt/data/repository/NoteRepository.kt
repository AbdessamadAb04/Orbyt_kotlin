package com.abdessamad.orbyt.data.repository

import com.abdessamad.orbyt.data.local.dao.NoteDao
import com.abdessamad.orbyt.data.local.entity.Note
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()

    fun getArchivedNotes(): Flow<List<Note>> = noteDao.getArchivedNotes()

    fun getPinnedNote(): Flow<Note?> = noteDao.getPinnedNote()

    fun searchNotes(query: String): Flow<List<Note>> =
        noteDao.searchNotes(query)

    suspend fun insertNote(note: Note) = noteDao.insertNote(note)
    suspend fun updateNote(note: Note) = noteDao.updateNote(note)
    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    suspend fun clearArchivedNotes() = noteDao.clearArchivedNotes()

    suspend fun togglePin(note: Note) =
        noteDao.updateNote(note.copy(isPinned = !note.isPinned))
}