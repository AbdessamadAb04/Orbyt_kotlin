package com.abdessamad.orbyt.data.dao

import androidx.room.*
import com.abdessamad.orbyt.data.models.Appointment
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface AppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appointment: Appointment)

    @Update
    suspend fun update(appointment: Appointment)

    @Delete
    suspend fun delete(appointment: Appointment)

    @Query("SELECT * FROM appointments WHERE id = :id")
    fun getAppointmentById(id: UUID): Flow<Appointment>

    @Query("SELECT * FROM appointments ORDER BY dateTime DESC")
    fun getAllAppointments(): Flow<List<Appointment>>
}