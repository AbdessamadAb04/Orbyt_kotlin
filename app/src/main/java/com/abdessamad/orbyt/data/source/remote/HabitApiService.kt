package com.abdessamad.orbyt.data.source.remote

import com.abdessamad.orbyt.data.models.Habit
import retrofit2.http.GET

interface HabitApiService {
    @GET("habits")
    suspend fun getHabits(): List<Habit>
}