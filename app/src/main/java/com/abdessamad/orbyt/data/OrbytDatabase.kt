package com.abdessamad.orbyt.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abdessamad.orbyt.data.dao.*
import com.abdessamad.orbyt.data.models.*

@Database(
    entities = [
        Appointment::class,
        Habit::class,
        HabitLog::class,
        Nebula::class,
        Note::class,
        NoteFolder::class,
        Task::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class OrbytDatabase : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentDao
    abstract fun habitDao(): HabitDao
    abstract fun nebulaDao(): NebulaDao
    abstract fun noteDao(): NoteDao
    abstract fun taskDao(): TaskDao
}