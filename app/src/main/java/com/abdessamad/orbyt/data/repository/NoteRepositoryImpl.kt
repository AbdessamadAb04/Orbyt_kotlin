package com.abdessamad.orbyt.data.repository

import com.abdessamad.orbyt.data.dao.NoteDao
import com.abdessamad.orbyt.data.models.Note
import com.abdessamad.orbyt.data.models.NoteFolder
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val noteApiService: NoteApiService
) : NoteRepository {
    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAllNotes()
    override fun getNoteById(id: UUID): Flow<Note> = noteDao.getNoteById(id)
    override suspend fun insertNote(note: Note) = noteDao.insertNote(note)
    override suspend fun updateNote(note: Note) = noteDao.updateNote(note)
    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    override suspend fun refreshNotes() {
        val remoteNotes = noteApiService.getNotes()
        remoteNotes.forEach { noteDao.insertNote(it) }
    }

    override fun getAllNoteFolders(): Flow<List<NoteFolder>> = noteDao.getAllNoteFolders()
    override fun getNoteFolderById(id: UUID): Flow<NoteFolder> = noteDao.getNoteFolderById(id)
    override suspend fun insertNoteFolder(folder: NoteFolder) = noteDao.insertNoteFolder(folder)
    override suspend fun updateNoteFolder(folder: NoteFolder) = noteDao.updateNoteFolder(folder)
    override suspend fun deleteNoteFolder(folder: NoteFolder) = noteDao.deleteNoteFolder(folder)
    override suspend fun refreshNoteFolders() {
        val remoteNoteFolders = noteApiService.getNoteFolders()
        remoteNoteFolders.forEach { noteDao.insertNoteFolder(it) }
    }
}