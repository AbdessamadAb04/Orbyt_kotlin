package com.abdessamad.orbyt.data.local.dao

import androidx.room.*
import com.abdessamad.orbyt.data.local.entity.Habit
import com.abdessamad.orbyt.data.local.entity.HabitLog
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Query("SELECT * FROM habits WHERE isArchived = 0 ORDER BY createdAt ASC")
    fun getAllHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE isArchived = 1 ORDER BY createdAt DESC")
    fun getArchivedHabits(): Flow<List<Habit>>

    @Query("DELETE FROM habits WHERE isArchived = 1")
    suspend fun clearArchivedHabits()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    // --- HabitLog queries ---

    @Query("SELECT * FROM habit_logs WHERE date = :date")
    fun getLogsForDate(date: String): Flow<List<HabitLog>>

    @Query("SELECT * FROM habit_logs WHERE habitId = :habitId ORDER BY date ASC")
    fun getLogsForHabit(habitId: Long): Flow<List<HabitLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: HabitLog)
}