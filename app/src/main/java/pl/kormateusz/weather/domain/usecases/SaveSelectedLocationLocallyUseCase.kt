package pl.kormateusz.weather.domain.usecases

import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.domain.repositories.LocationRepository

class SaveSelectedLocationLocallyUseCase(
    private val locationRepository: LocationRepository,
) : BaseParamUseCase<Location, Unit>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun buildUseCase(location: Location) {
        locationRepository.saveLocationLocally(location)
    }
}