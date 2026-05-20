package com.abdessamad.orbyt.data.source.remote

import com.abdessamad.orbyt.data.models.Task
import retrofit2.http.GET

interface TaskApiService {
    @GET("tasks")
    suspend fun getTasks(): List<Task>
}