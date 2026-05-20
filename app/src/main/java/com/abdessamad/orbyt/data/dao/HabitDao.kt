package com.abdessamad.orbyt.data.dao

import androidx.room.*
import com.abdessamad.orbyt.data.models.Habit
import com.abdessamad.orbyt.data.models.HabitLog
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habits WHERE id = :id")
    fun getHabitById(id: UUID): Flow<Habit>

    @Query("SELECT * FROM habits ORDER BY createdAt DESC")
    fun getAllHabits(): Flow<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitLog(habitLog: HabitLog)

    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId ORDER BY logDate DESC")
    fun getHabitLogs(habitId: UUID): Flow<List<HabitLog>>
}