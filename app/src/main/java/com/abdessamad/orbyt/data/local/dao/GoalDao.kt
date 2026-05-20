package com.abdessamad.orbyt.data.local.dao

import androidx.room.*
import com.abdessamad.orbyt.data.local.entity.Goal
import com.abdessamad.orbyt.data.local.entity.GoalStatus
import com.abdessamad.orbyt.data.local.entity.GoalStep
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {

    @Query("SELECT * FROM goals WHERE isArchived = 0 ORDER BY targetDate ASC")
    fun getAllGoals(): Flow<List<Goal>>

    @Query("SELECT * FROM goals WHERE isArchived = 1 ORDER BY targetDate ASC")
    fun getArchivedGoals(): Flow<List<Goal>>

    @Query("SELECT * FROM goals WHERE status = :status AND isArchived = 0")
    fun getGoalsByStatus(status: GoalStatus): Flow<List<Goal>>

    @Query("DELETE FROM goals WHERE isArchived = 1")
    suspend fun clearArchivedGoals()

    @Query("SELECT * FROM goal_steps WHERE goalId = :goalId")
    fun getStepsForGoal(goalId: Long): Flow<List<GoalStep>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal)

    @Update
    suspend fun updateGoal(goal: Goal)

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStep(step: GoalStep)

    @Update
    suspend fun updateStep(step: GoalStep)

    @Delete
    suspend fun deleteStep(step: GoalStep)
}