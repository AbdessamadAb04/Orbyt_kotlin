package com.abdessamad.orbyt.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdessamad.orbyt.data.local.entity.Habit
import com.abdessamad.orbyt.data.repository.HabitRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HabitViewModel(private val repository: HabitRepository) : ViewModel() {

    val allHabits: StateFlow<List<Habit>> = repository.getAllHabits()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val archivedHabits: StateFlow<List<Habit>> = repository.getArchivedHabits()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayLogs = repository.getLogsForToday()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertHabit(habit: Habit) {
        viewModelScope.launch { repository.insertHabit(habit) }
    }

    fun archiveHabit(habit: Habit) {
        viewModelScope.launch { repository.updateHabit(habit.copy(isArchived = true)) }
    }

    fun unarchiveHabit(habit: Habit) {
        viewModelScope.launch { repository.updateHabit(habit.copy(isArchived = false)) }
    }

    fun clearArchivedHabits() {
        viewModelScope.launch { repository.clearArchivedHabits() }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch { repository.deleteHabit(habit) }
    }

    fun toggleHabit(habitId: Long, isDone: Boolean) {
        viewModelScope.launch { repository.toggleHabitLog(habitId, isDone) }
    }
}