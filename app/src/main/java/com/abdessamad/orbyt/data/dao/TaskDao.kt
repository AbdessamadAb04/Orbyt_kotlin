package com.abdessamad.orbyt.data.dao

import androidx.room.*
import com.abdessamad.orbyt.data.models.Task
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: UUID): Flow<Task>

    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAllTasks(): Flow<List<Task>>
}