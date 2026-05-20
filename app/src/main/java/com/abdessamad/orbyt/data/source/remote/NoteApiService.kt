package com.abdessamad.orbyt.data.source.remote

import com.abdessamad.orbyt.data.models.Note
import com.abdessamad.orbyt.data.models.NoteFolder
import retrofit2.http.GET

interface NoteApiService {
    @GET("notes")
    suspend fun getNotes(): List<Note>

    @GET("note_folders")
    suspend fun getNoteFolders(): List<NoteFolder>
}