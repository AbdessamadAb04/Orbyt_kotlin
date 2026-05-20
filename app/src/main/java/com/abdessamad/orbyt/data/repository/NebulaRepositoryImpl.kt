package com.abdessamad.orbyt.data.repository

import com.abdessamad.orbyt.data.dao.NebulaDao
import com.abdessamad.orbyt.data.models.Nebula
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class NebulaRepositoryImpl @Inject constructor(
    private val nebulaDao: NebulaDao,
    private val nebulaApiService: NebulaApiService
) : NebulaRepository {
    override fun getAllNebulas(): Flow<List<Nebula>> = nebulaDao.getAllNebulas()
    override fun getNebulaById(id: UUID): Flow<Nebula> = nebulaDao.getNebulaById(id)
    override suspend fun insert(nebula: Nebula) = nebulaDao.insert(nebula)
    override suspend fun update(nebula: Nebula) = nebulaDao.update(nebula)
    override suspend fun delete(nebula: Nebula) = nebulaDao.delete(nebula)
    override suspend fun refreshNebulas() {
        val remoteNebulas = nebulaApiService.getNebulas()
        remoteNebulas.forEach { nebulaDao.insert(it) }
    }
}