package pl.kormateusz.weather.data.repositories

import pl.kormateusz.weather.data.models.responses.toDomain
import pl.kormateusz.weather.data.services.WeatherService
import pl.kormateusz.weather.data.utils.makeRequest
import pl.kormateusz.weather.domain.models.Forecast
import pl.kormateusz.weather.domain.models.Weather
import pl.kormateusz.weather.domain.repositories.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherService: WeatherService,
) : WeatherRepository {

    override suspend fun getWeatherForLocation(
        locationId: String,
        languageCode: String,
    ): Result<Weather> =
        weatherService.getCurrentWeather(locationId, languageCode)
            .makeRequest { it.first().toDomain() }

    override suspend fun getForecastForLocation(
        locationId: String,
        languageCode: String
    ): Result<List<Forecast>> = weatherService.getForecast(locationId, languageCode)
        .makeRequest { it.toDomain() }
}