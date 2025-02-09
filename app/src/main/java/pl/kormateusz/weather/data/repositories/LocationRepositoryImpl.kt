package pl.kormateusz.weather.data.repositories

import pl.kormateusz.weather.data.database.daos.LocationDao
import pl.kormateusz.weather.data.models.entities.toDatabaseEntity
import pl.kormateusz.weather.data.models.entities.toDomain
import pl.kormateusz.weather.data.models.responses.toDomain
import pl.kormateusz.weather.data.services.LocationService
import pl.kormateusz.weather.data.utils.makeRequest
import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.domain.repositories.LocationRepository

class LocationRepositoryImpl(
    private val locationService: LocationService,
    private val locationDao: LocationDao,
) : LocationRepository {

    override suspend fun getLocations(query: String, languageCode: String): Result<List<Location>> =
        locationService.getLocations(query = query, languageCode = languageCode)
            .makeRequest { it.toDomain() }

    override fun saveLocationLocally(location: Location) {
        locationDao.insert(location.toDatabaseEntity())
    }

    override suspend fun getSavedLocations(): List<Location> =
        locationDao.get().map { it.toDomain() }
}