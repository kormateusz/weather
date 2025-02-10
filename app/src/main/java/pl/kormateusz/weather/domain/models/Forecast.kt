package pl.kormateusz.weather.domain.models

import java.time.OffsetDateTime

data class Forecast(
    val dateTime: OffsetDateTime,
    val minTemperature: String,
    val maxTemperature: String,
    val condition: WeatherCondition,
)