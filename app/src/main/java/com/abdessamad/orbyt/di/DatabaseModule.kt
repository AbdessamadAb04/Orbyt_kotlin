package com.abdessamad.orbyt.di

import android.content.Context
import androidx.room.Room
import com.abdessamad.orbyt.data.OrbytDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): OrbytDatabase {
        return Room.databaseBuilder(
            context,
            OrbytDatabase::class.java,
            "orbyt_database"
        ).build()
    }

    @Provides
    fun provideAppointmentDao(database: OrbytDatabase) = database.appointmentDao()

    @Provides
    fun provideHabitDao(database: OrbytDatabase) = database.habitDao()

    @Provides
    fun provideNebulaDao(database: OrbytDatabase) = database.nebulaDao()

    @Provides
    fun provideNoteDao(database: OrbytDatabase) = database.noteDao()

    @Provides
    fun provideTaskDao(database: OrbytDatabase) = database.taskDao()
}