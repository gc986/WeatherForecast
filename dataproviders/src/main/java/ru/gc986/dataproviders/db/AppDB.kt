package ru.gc986.dataproviders.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.gc986.dataproviders.db.converters.EducationPeriodConverter
import ru.gc986.models.user.User

@Database(entities = [User::class], version = 1)
@TypeConverters(EducationPeriodConverter::class)
abstract class AppDB: RoomDatabase() {

    abstract fun getUserDao():UserDao

}