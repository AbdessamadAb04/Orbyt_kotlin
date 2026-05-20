package com.abdessamad.orbyt.data.repository

import com.abdessamad.orbyt.data.local.dao.TaskDao
import com.abdessamad.orbyt.data.local.dao.ProjectDao
import com.abdessamad.orbyt.data.local.entity.Project
import com.abdessamad.orbyt.data.local.entity.Status
import com.abdessamad.orbyt.data.local.entity.Task
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

class TaskRepository(
    private val taskDao: TaskDao,
    private val projectDao: ProjectDao
) {
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    fun getArchivedTasks(): Flow<List<Task>> = taskDao.getArchivedTasks()

    fun getTasksByProject(projectId: Long): Flow<List<Task>> =
        taskDao.getTasksByProject(projectId)

    fun getTopPriorityTasks(): Flow<List<Task>> =
        taskDao.getTopPriorityTasks()

    fun getTasksForToday(): Flow<List<Task>> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startOfDay = calendar.timeInMillis

        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        val endOfDay = calendar.timeInMillis

        return taskDao.getTasksForToday(startOfDay, endOfDay)
    }

    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    suspend fun clearArchivedTasks() = taskDao.clearArchivedTasks()

    fun getAllProjects(): Flow<List<Project>> = projectDao.getAllProjects()
    suspend fun insertProject(project: Project) = projectDao.insertProject(project)
    suspend fun updateProject(project: Project) = projectDao.updateProject(project)
    suspend fun deleteProject(project: Project) = projectDao.deleteProject(project)
}