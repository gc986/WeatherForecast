package ru.gc986.models.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    val biography: String,
    val educationPeriod: EducationPeriod,
    val height: Double,
    @PrimaryKey val id: String,
    val name: String,
    val phone: String,
    val temperament: String
) : Serializable