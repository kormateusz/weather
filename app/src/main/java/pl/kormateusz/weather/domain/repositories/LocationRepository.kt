package pl.kormateusz.weather.domain.repositories

import pl.kormateusz.weather.domain.models.Location

interface LocationRepository {
    suspend fun getLocations(query: String, languageCode: String): Result<List<Location>>

    fun saveLocationLocally(location: Location)

    suspend fun getSavedLocations(): List<Location>
}