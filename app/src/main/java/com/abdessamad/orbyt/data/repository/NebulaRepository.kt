package com.abdessamad.orbyt.data.repository

import com.abdessamad.orbyt.data.models.Nebula
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface NebulaRepository {
    fun getAllNebulas(): Flow<List<Nebula>>
    fun getNebulaById(id: UUID): Flow<Nebula>
    suspend fun insert(nebula: Nebula)
    suspend fun update(nebula: Nebula)
    suspend fun delete(nebula: Nebula)
    suspend fun refreshNebulas()
}