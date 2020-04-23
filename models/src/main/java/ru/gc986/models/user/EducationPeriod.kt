package ru.gc986.models.user

import java.io.Serializable

data class EducationPeriod(
    val end: String,
    val start: String
) : Serializable