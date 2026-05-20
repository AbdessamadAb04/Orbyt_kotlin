package com.abdessamad.orbyt.data.local.dao

import androidx.room.*
import com.abdessamad.orbyt.data.local.entity.Status
import com.abdessamad.orbyt.data.local.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks WHERE isArchived = 0 ORDER BY dueDate ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE isArchived = 1 ORDER BY createdAt DESC")
    fun getArchivedTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE projectId = :projectId AND isArchived = 0")
    fun getTasksByProject(projectId: Long): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE (dueDate BETWEEN :startOfDay AND :endOfDay) AND isArchived = 0")
    fun getTasksForToday(startOfDay: Long, endOfDay: Long): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE status != :doneStatus AND isArchived = 0 ORDER BY priority ASC LIMIT 3")
    fun getTopPriorityTasks(doneStatus: Status = Status.DONE): Flow<List<Task>>

    @Query("DELETE FROM tasks WHERE isArchived = 1")
    suspend fun clearArchivedTasks()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}