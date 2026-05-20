package com.abdessamad.orbyt.data.source.remote

import com.abdessamad.orbyt.data.models.Appointment
import retrofit2.http.GET

interface AppointmentApiService {
    @GET("appointments")
    suspend fun getAppointments(): List<Appointment>
}