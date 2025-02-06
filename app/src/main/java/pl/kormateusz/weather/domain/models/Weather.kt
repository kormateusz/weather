package pl.kormateusz.weather.domain.models

import java.time.OffsetDateTime

data class Weather(
    val dateTime: OffsetDateTime,
    val weatherText: String,
    val temperature: String,
)
