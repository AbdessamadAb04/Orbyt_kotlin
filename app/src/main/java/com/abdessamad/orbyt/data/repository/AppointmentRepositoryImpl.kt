package com.abdessamad.orbyt.data.repository

import com.abdessamad.orbyt.data.dao.AppointmentDao
import com.abdessamad.orbyt.data.models.Appointment
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class AppointmentRepositoryImpl @Inject constructor(
    private val appointmentDao: AppointmentDao,
    private val appointmentApiService: AppointmentApiService
) : AppointmentRepository {
    override fun getAllAppointments(): Flow<List<Appointment>> = appointmentDao.getAllAppointments()
    override fun getAppointmentById(id: UUID): Flow<Appointment> = appointmentDao.getAppointmentById(id)
    override suspend fun insert(appointment: Appointment) = appointmentDao.insert(appointment)
    override suspend fun update(appointment: Appointment) = appointmentDao.update(appointment)
    override suspend fun delete(appointment: Appointment) = appointmentDao.delete(appointment)
    override suspend fun refreshAppointments() {
        val remoteAppointments = appointmentApiService.getAppointments()
        remoteAppointments.forEach { appointmentDao.insert(it) }
    }
}