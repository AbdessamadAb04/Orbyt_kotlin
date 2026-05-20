package com.abdessamad.orbyt.data.repository

import com.abdessamad.orbyt.data.local.dao.HabitDao
import com.abdessamad.orbyt.data.local.entity.Habit
import com.abdessamad.orbyt.data.local.entity.HabitLog
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class HabitRepository(private val habitDao: HabitDao) {

    fun getAllHabits(): Flow<List<Habit>> = habitDao.getAllHabits()

    fun getArchivedHabits(): Flow<List<Habit>> = habitDao.getArchivedHabits()

    fun getLogsForToday(): Flow<List<HabitLog>> {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(Date())
        return habitDao.getLogsForDate(today)
    }

    fun getLogsForHabit(habitId: Long): Flow<List<HabitLog>> =
        habitDao.getLogsForHabit(habitId)

    suspend fun insertHabit(habit: Habit) = habitDao.insertHabit(habit)
    suspend fun updateHabit(habit: Habit) = habitDao.insertHabit(habit)
    suspend fun deleteHabit(habit: Habit) = habitDao.deleteHabit(habit)
    suspend fun clearArchivedHabits() = habitDao.clearArchivedHabits()

    suspend fun toggleHabitLog(habitId: Long, isDone: Boolean) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(Date())
        habitDao.insertLog(
            HabitLog(habitId = habitId, date = today, isDone = isDone)
        )
    }
}