package pl.kormateusz.weather.domain.repositories

import pl.kormateusz.weather.domain.models.Weather

interface WeatherRepository {
    suspend fun getWeatherForLocation(locationId: String, languageCode: String): Result<Weather>
}