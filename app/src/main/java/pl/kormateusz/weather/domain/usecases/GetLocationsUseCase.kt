package pl.kormateusz.weather.domain.usecases

import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.domain.repositories.LocationRepository

class GetLocationsUseCase(
    private val locationRepository: LocationRepository,
    private val getCurrentLanguageCodeUseCase: GetCurrentLanguageCodeUseCase,
) : BaseParamSuspendUseCase<String, Result<List<Location>>>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun buildUseCase(query: String): Result<List<Location>> {
        if (query.isEmpty()) return Result.success(emptyList())
        return locationRepository.getLocations(
            query = query.trim(),
            languageCode = getCurrentLanguageCodeUseCase.execute()
        )
    }
}