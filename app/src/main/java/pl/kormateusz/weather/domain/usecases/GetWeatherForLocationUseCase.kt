package pl.kormateusz.weather.domain.usecases

import pl.kormateusz.weather.domain.models.Weather
import pl.kormateusz.weather.domain.repositories.WeatherRepository

class GetWeatherForLocationUseCase(
    private val weatherRepository: WeatherRepository,
    private val getCurrentLanguageCodeUseCase: GetCurrentLanguageCodeUseCase,
) : BaseParamSuspendUseCase<String, Result<Weather>>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun buildUseCase(locationId: String): Result<Weather> =
        weatherRepository.getWeatherForLocation(
            locationId = locationId,
            languageCode = getCurrentLanguageCodeUseCase.execute()
        )
}