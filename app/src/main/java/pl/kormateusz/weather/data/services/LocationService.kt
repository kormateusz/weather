package pl.kormateusz.weather.data.services

import pl.kormateusz.weather.data.models.responses.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {
    @GET("locations/v1/cities/autocomplete")
    suspend fun getLocations(
        @Query("q") query: String,
        @Query("language") languageCode: String,
    ): Response<List<LocationResponse>>
}