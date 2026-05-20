package com.abdessamad.orbyt.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdessamad.orbyt.data.local.dao.*
import com.abdessamad.orbyt.data.local.entity.*
import androidx.room.TypeConverters

@TypeConverters(OrbytTypeConverters::class)
@Database(
    entities = [
        Task::class,
        Project::class,
        Habit::class,
        HabitLog::class,
        Appointment::class,
        Note::class,
        Goal::class,
        GoalStep::class
    ],
    version = 2,
    exportSchema = false
)
abstract class OrbytDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun projectDao(): ProjectDao
    abstract fun habitDao(): HabitDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun noteDao(): NoteDao
    abstract fun goalDao(): GoalDao

    companion object {
        @Volatile
        private var INSTANCE: OrbytDatabase? = null

        fun getInstance(context: Context): OrbytDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    OrbytDatabase::class.java,
                    "orbyt_database"
                )
                .fallbackToDestructiveMigration()
                .build().also { INSTANCE = it }
            }
        }
    }
}