package com.abdessamad.orbyt.di

import com.abdessamad.orbyt.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppointmentRepository(
        appointmentRepositoryImpl: AppointmentRepositoryImpl
    ): AppointmentRepository

    @Binds
    @Singleton
    abstract fun bindHabitRepository(
        habitRepositoryImpl: HabitRepositoryImpl
    ): HabitRepository

    @Binds
    @Singleton
    abstract fun bindNebulaRepository(
        nebulaRepositoryImpl: NebulaRepositoryImpl
    ): NebulaRepository

    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        noteRepositoryImpl: NoteRepositoryImpl
    ): NoteRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository
}