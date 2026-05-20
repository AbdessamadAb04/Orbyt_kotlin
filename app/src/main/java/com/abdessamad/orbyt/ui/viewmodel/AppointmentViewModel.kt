package com.abdessamad.orbyt.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdessamad.orbyt.data.local.entity.Appointment
import com.abdessamad.orbyt.data.repository.AppointmentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppointmentViewModel(private val repository: AppointmentRepository) : ViewModel() {

    val allAppointments: StateFlow<List<Appointment>> = repository.getAllAppointments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val archivedAppointments: StateFlow<List<Appointment>> = repository.getArchivedAppointments()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val nextAppointment: StateFlow<Appointment?> = repository.getNextAppointment()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val ddays: StateFlow<List<Appointment>> = repository.getDDays()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertAppointment(appointment: Appointment) {
        viewModelScope.launch { repository.insertAppointment(appointment) }
    }

    fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch { repository.updateAppointment(appointment) }
    }

    fun archiveAppointment(appointment: Appointment) {
        viewModelScope.launch { repository.updateAppointment(appointment.copy(isArchived = true)) }
    }

    fun unarchiveAppointment(appointment: Appointment) {
        viewModelScope.launch { repository.updateAppointment(appointment.copy(isArchived = false)) }
    }

    fun clearArchivedAppointments() {
        viewModelScope.launch { repository.clearArchivedAppointments() }
    }

    fun deleteAppointment(appointment: Appointment) {
        viewModelScope.launch { repository.deleteAppointment(appointment) }
    }
}