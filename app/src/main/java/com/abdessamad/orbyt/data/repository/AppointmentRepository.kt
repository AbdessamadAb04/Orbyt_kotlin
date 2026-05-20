package com.abdessamad.orbyt.data.repository

import com.abdessamad.orbyt.data.local.dao.AppointmentDao
import com.abdessamad.orbyt.data.local.entity.Appointment
import kotlinx.coroutines.flow.Flow

class AppointmentRepository(private val appointmentDao: AppointmentDao) {

    fun getAllAppointments(): Flow<List<Appointment>> =
        appointmentDao.getAllAppointments()

    fun getArchivedAppointments(): Flow<List<Appointment>> =
        appointmentDao.getArchivedAppointments()

    fun getNextAppointment(): Flow<Appointment?> =
        appointmentDao.getNextAppointment(System.currentTimeMillis())

    fun getDDays(): Flow<List<Appointment>> =
        appointmentDao.getDDays()

    suspend fun insertAppointment(appointment: Appointment) =
        appointmentDao.insertAppointment(appointment)

    suspend fun updateAppointment(appointment: Appointment) =
        appointmentDao.updateAppointment(appointment)

    suspend fun deleteAppointment(appointment: Appointment) =
        appointmentDao.deleteAppointment(appointment)

    suspend fun clearArchivedAppointments() =
        appointmentDao.clearArchivedAppointments()
}