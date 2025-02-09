package pl.kormateusz.weather.domain.usecases

import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.domain.repositories.LocationRepository

class GetLocallySavedLocationsUseCase(
    private val locationRepository: LocationRepository,
) : BaseSuspendUseCase<List<Location>>() {

    override suspend fun buildUseCase(): List<Location> =
        locationRepository.getSavedLocations()
}