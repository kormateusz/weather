package pl.kormateusz.weather.domain.usecases

import pl.kormateusz.weather.domain.models.Forecast
import pl.kormateusz.weather.domain.repositories.WeatherRepository

class GetForecastForLocationUseCase(
    private val weatherRepository: WeatherRepository,
    private val getCurrentLanguageCodeUseCase: GetCurrentLanguageCodeUseCase,
) : BaseParamSuspendUseCase<String, Result<List<Forecast>>>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun buildUseCase(locationId: String): Result<List<Forecast>> =
        weatherRepository.getForecastForLocation(
            locationId = locationId,
            languageCode = getCurrentLanguageCodeUseCase.execute()
        )
}