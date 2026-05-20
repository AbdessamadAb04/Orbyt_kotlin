package com.abdessamad.orbyt.data.local.dao

import androidx.room.*
import com.abdessamad.orbyt.data.local.entity.Appointment
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {

    @Query("SELECT * FROM appointments WHERE isArchived = 0 ORDER BY dateTime ASC")
    fun getAllAppointments(): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE isArchived = 1 ORDER BY dateTime ASC")
    fun getArchivedAppointments(): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE dateTime >= :from AND isArchived = 0 ORDER BY dateTime ASC LIMIT 1")
    fun getNextAppointment(from: Long): Flow<Appointment?>

    @Query("SELECT * FROM appointments WHERE isDDay = 1 AND isArchived = 0 ORDER BY dateTime ASC")
    fun getDDays(): Flow<List<Appointment>>

    @Query("DELETE FROM appointments WHERE isArchived = 1")
    suspend fun clearArchivedAppointments()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointment)

    @Update
    suspend fun updateAppointment(appointment: Appointment)

    @Delete
    suspend fun deleteAppointment(appointment: Appointment)
}