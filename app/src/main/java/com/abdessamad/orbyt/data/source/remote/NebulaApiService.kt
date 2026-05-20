package com.abdessamad.orbyt.data.source.remote

import com.abdessamad.orbyt.data.models.Nebula
import retrofit2.http.GET

interface NebulaApiService {
    @GET("nebulas")
    suspend fun getNebulas(): List<Nebula>
}