package com.abdessamad.orbyt.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdessamad.orbyt.data.local.entity.Note
import com.abdessamad.orbyt.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val pinnedNote: StateFlow<Note?> = repository.getPinnedNote()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val archivedNotes: StateFlow<List<Note>> = repository.getArchivedNotes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // search query state — empty string means show all notes
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val notes: StateFlow<List<Note>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isEmpty()) repository.getAllNotes()
            else repository.searchNotes(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun insertNote(note: Note) {
        viewModelScope.launch { repository.insertNote(note) }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch { repository.updateNote(note) }
    }

    fun archiveNote(note: Note) {
        viewModelScope.launch { repository.updateNote(note.copy(isArchived = true)) }
    }

    fun unarchiveNote(note: Note) {
        viewModelScope.launch { repository.updateNote(note.copy(isArchived = false)) }
    }

    fun clearArchivedNotes() {
        viewModelScope.launch { repository.clearArchivedNotes() }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch { repository.deleteNote(note) }
    }

    fun togglePin(note: Note) {
        viewModelScope.launch { repository.togglePin(note) }
    }
}