package com.abdessamad.orbyt.data.repository

import com.abdessamad.orbyt.data.local.dao.GoalDao
import com.abdessamad.orbyt.data.local.entity.Goal
import com.abdessamad.orbyt.data.local.entity.GoalStatus
import com.abdessamad.orbyt.data.local.entity.GoalStep
import kotlinx.coroutines.flow.Flow

class GoalRepository(private val goalDao: GoalDao) {

    fun getAllGoals(): Flow<List<Goal>> = goalDao.getAllGoals()

    fun getArchivedGoals(): Flow<List<Goal>> = goalDao.getArchivedGoals()

    fun getGoalsByStatus(status: GoalStatus): Flow<List<Goal>> =
        goalDao.getGoalsByStatus(status)

    fun getStepsForGoal(goalId: Long): Flow<List<GoalStep>> =
        goalDao.getStepsForGoal(goalId)

    suspend fun insertGoal(goal: Goal) = goalDao.insertGoal(goal)
    suspend fun updateGoal(goal: Goal) = goalDao.updateGoal(goal)
    suspend fun deleteGoal(goal: Goal) = goalDao.deleteGoal(goal)
    suspend fun clearArchivedGoals() = goalDao.clearArchivedGoals()

    suspend fun insertStep(step: GoalStep) = goalDao.insertStep(step)
    suspend fun updateStep(step: GoalStep) = goalDao.updateStep(step)
    suspend fun deleteStep(step: GoalStep) = goalDao.deleteStep(step)

    suspend fun markGoalAchieved(goal: Goal) =
        goalDao.updateGoal(goal.copy(status = GoalStatus.ACHIEVED))
}