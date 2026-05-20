package com.abdessamad.orbyt.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdessamad.orbyt.data.local.entity.Task
import com.abdessamad.orbyt.data.local.entity.Project
import com.abdessamad.orbyt.data.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val allTasks: StateFlow<List<Task>> = repository.getAllTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val archivedTasks: StateFlow<List<Task>> = repository.getArchivedTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val todayTasks: StateFlow<List<Task>> = repository.getTasksForToday()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val topPriorityTasks: StateFlow<List<Task>> = repository.getTopPriorityTasks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allProjects: StateFlow<List<Project>> = repository.getAllProjects()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertTask(task: Task) {
        viewModelScope.launch { repository.insertTask(task) }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch { repository.updateTask(task) }
    }

    fun archiveTask(task: Task) {
        viewModelScope.launch { repository.updateTask(task.copy(isArchived = true)) }
    }

    fun unarchiveTask(task: Task) {
        viewModelScope.launch { repository.updateTask(task.copy(isArchived = false)) }
    }

    fun clearArchivedTasks() {
        viewModelScope.launch { repository.clearArchivedTasks() }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch { repository.deleteTask(task) }
    }

    fun insertProject(project: Project) {
        viewModelScope.launch { repository.insertProject(project) }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch { repository.deleteProject(project) }
    }
}