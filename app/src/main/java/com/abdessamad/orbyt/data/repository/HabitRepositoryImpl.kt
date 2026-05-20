package com.abdessamad.orbyt.data.repository

import com.abdessamad.orbyt.data.dao.HabitDao
import com.abdessamad.orbyt.data.models.Habit
import com.abdessamad.orbyt.data.models.HabitLog
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class HabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao,
    private val habitApiService: HabitApiService
) : HabitRepository {
    override fun getAllHabits(): Flow<List<Habit>> = habitDao.getAllHabits()
    override fun getHabitById(id: UUID): Flow<Habit> = habitDao.getHabitById(id)
    override fun getHabitLogs(habitId: UUID): Flow<List<HabitLog>> = habitDao.getHabitLogs(habitId)
    override suspend fun insertHabit(habit: Habit) = habitDao.insertHabit(habit)
    override suspend fun updateHabit(habit: Habit) = habitDao.updateHabit(habit)
    override suspend fun deleteHabit(habit: Habit) = habitDao.deleteHabit(habit)
    override suspend fun insertHabitLog(habitLog: HabitLog) = habitDao.insertHabitLog(habitLog)
    override suspend fun refreshHabits() {
        val remoteHabits = habitApiService.getHabits()
        remoteHabits.forEach { habitDao.insertHabit(it) }
    }
}