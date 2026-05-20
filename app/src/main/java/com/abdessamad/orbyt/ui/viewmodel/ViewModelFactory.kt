package com.abdessamad.orbyt.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdessamad.orbyt.data.local.OrbytDatabase
import com.abdessamad.orbyt.data.repository.AppointmentRepository
import com.abdessamad.orbyt.data.repository.GoalRepository
import com.abdessamad.orbyt.data.repository.HabitRepository
import com.abdessamad.orbyt.data.repository.NoteRepository
import com.abdessamad.orbyt.data.repository.TaskRepository

class ViewModelFactory(
    private val database: OrbytDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val taskRepo = TaskRepository(database.taskDao(), database.projectDao())
        val habitRepo = HabitRepository(database.habitDao())
        val appointmentRepo = AppointmentRepository(database.appointmentDao())
        val noteRepo = NoteRepository(database.noteDao())
        val goalRepo = GoalRepository(database.goalDao())

        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(TaskViewModel::class.java) ->
                TaskViewModel(taskRepo) as T

            modelClass.isAssignableFrom(HabitViewModel::class.java) ->
                HabitViewModel(habitRepo) as T

            modelClass.isAssignableFrom(AppointmentViewModel::class.java) ->
                AppointmentViewModel(appointmentRepo) as T

            modelClass.isAssignableFrom(NoteViewModel::class.java) ->
                NoteViewModel(noteRepo) as T

            modelClass.isAssignableFrom(GoalViewModel::class.java) ->
                GoalViewModel(goalRepo) as T

            else -> throw IllegalArgumentException("Unknown ViewModel: ${modelClass.name}")
        }
    }
}