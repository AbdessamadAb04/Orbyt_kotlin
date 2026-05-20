package com.abdessamad.orbyt.data.repository

import com.abdessamad.orbyt.data.dao.TaskDao
import com.abdessamad.orbyt.data.models.Task
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val taskApiService: TaskApiService
) : TaskRepository {
    override fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()
    override fun getTaskById(id: UUID): Flow<Task> = taskDao.getTaskById(id)
    override suspend fun insert(task: Task) = taskDao.insert(task)
    override suspend fun update(task: Task) = taskDao.update(task)
    override suspend fun delete(task: Task) = taskDao.delete(task)
    override suspend fun refreshTasks() {
        val remoteTasks = taskApiService.getTasks()
        remoteTasks.forEach { taskDao.insert(it) }
    }
}