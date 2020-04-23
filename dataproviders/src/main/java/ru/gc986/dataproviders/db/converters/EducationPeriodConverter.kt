package ru.gc986.dataproviders.db.converters

import androidx.room.TypeConverter
import ru.gc986.models.user.EducationPeriod

class EducationPeriodConverter {

    val SEPARATOR = ","
    val INDEX_PEDIOD_END = 0
    val INDEX_PERIOD_START = 1

    @TypeConverter
    fun fromEducationPeriod(educationPeriod: EducationPeriod): String =
        "${educationPeriod.end}$SEPARATOR${educationPeriod.start}"

    @TypeConverter
    fun toEducationPeriod(data: String): EducationPeriod =
        EducationPeriod(
            data.split(SEPARATOR)[INDEX_PEDIOD_END],
            data.split(",")[INDEX_PERIOD_START]
        )

}