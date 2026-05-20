package com.abdessamad.orbyt.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdessamad.orbyt.data.local.entity.Goal
import com.abdessamad.orbyt.data.local.entity.GoalStep
import com.abdessamad.orbyt.data.repository.GoalRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GoalViewModel(private val repository: GoalRepository) : ViewModel() {

    val allGoals: StateFlow<List<Goal>> = repository.getAllGoals()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val archivedGoals: StateFlow<List<Goal>> = repository.getArchivedGoals()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getStepsForGoal(goalId: Long) = repository.getStepsForGoal(goalId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertGoal(goal: Goal) {
        viewModelScope.launch { repository.insertGoal(goal) }
    }

    fun updateGoal(goal: Goal) {
        viewModelScope.launch { repository.updateGoal(goal) }
    }

    fun archiveGoal(goal: Goal) {
        viewModelScope.launch { repository.updateGoal(goal.copy(isArchived = true)) }
    }

    fun unarchiveGoal(goal: Goal) {
        viewModelScope.launch { repository.updateGoal(goal.copy(isArchived = false)) }
    }

    fun clearArchivedGoals() {
        viewModelScope.launch { repository.clearArchivedGoals() }
    }

    fun deleteGoal(goal: Goal) {
        viewModelScope.launch { repository.deleteGoal(goal) }
    }

    fun insertStep(step: GoalStep) {
        viewModelScope.launch { repository.insertStep(step) }
    }

    fun updateStep(step: GoalStep) {
        viewModelScope.launch { repository.updateStep(step) }
    }

    fun markGoalAchieved(goal: Goal) {
        viewModelScope.launch { repository.markGoalAchieved(goal) }
    }
}