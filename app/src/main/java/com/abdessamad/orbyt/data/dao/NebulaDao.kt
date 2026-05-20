package com.abdessamad.orbyt.data.dao

import androidx.room.*
import com.abdessamad.orbyt.data.models.Nebula
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface NebulaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nebula: Nebula)

    @Update
    suspend fun update(nebula: Nebula)

    @Delete
    suspend fun delete(nebula: Nebula)

    @Query("SELECT * FROM nebulas WHERE id = :id")
    fun getNebulaById(id: UUID): Flow<Nebula>

    @Query("SELECT * FROM nebulas ORDER BY createdAt DESC")
    fun getAllNebulas(): Flow<List<Nebula>>
}