package com.abdessamad.orbyt.data.local

import androidx.room.TypeConverter
import com.abdessamad.orbyt.data.local.entity.*

class OrbytTypeConverters {

    @TypeConverter
    fun fromPriority(priority: Priority): String = priority.name

    @TypeConverter
    fun toPriority(value: String): Priority = Priority.valueOf(value)

    @TypeConverter
    fun fromStatus(status: Status): String = status.name

    @TypeConverter
    fun toStatus(value: String): Status = Status.valueOf(value)

    @TypeConverter
    fun fromGoalDomain(domain: GoalDomain): String = domain.name

    @TypeConverter
    fun toGoalDomain(value: String): GoalDomain = GoalDomain.valueOf(value)

    @TypeConverter
    fun fromGoalStatus(status: GoalStatus): String = status.name

    @TypeConverter
    fun toGoalStatus(value: String): GoalStatus = GoalStatus.valueOf(value)
}