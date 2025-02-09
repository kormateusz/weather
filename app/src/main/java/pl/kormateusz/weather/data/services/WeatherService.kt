package pl.kormateusz.weather.data.services

import pl.kormateusz.weather.data.models.responses.ForecastResponse
import pl.kormateusz.weather.data.models.responses.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("currentconditions/v1/{locationId}")
    suspend fun getCurrentWeather(
        @Path("locationId") locationId: String,
        @Query("language") languageCode: String,
    ): Response<List<WeatherResponse>>

    @GET("forecasts/v1/daily/5day/{locationId}")
    suspend fun getForecast(
        @Path("locationId") locationId: String,
        @Query("language") languageCode: String,
        @Query("metric") metric: Boolean = true,
    ): Response<ForecastResponse>
}