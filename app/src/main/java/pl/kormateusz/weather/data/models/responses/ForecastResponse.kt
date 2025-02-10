package pl.kormateusz.weather.data.models.responses

import com.google.gson.annotations.SerializedName
import pl.kormateusz.weather.domain.extensions.toOffsetDateTime
import pl.kormateusz.weather.domain.models.Forecast
import pl.kormateusz.weather.domain.models.WeatherCondition

data class ForecastResponse(
    @SerializedName("DailyForecasts") val forecasts: List<DailyForecastResponse>,
)

data class DailyForecastResponse(
    @SerializedName("Date") val date: String,
    @SerializedName("Temperature") val temperature: ForecastTemperatureResponse,
    @SerializedName("Day") val day: ForecastDayResponse,
)

data class ForecastTemperatureResponse(
    @SerializedName("Minimum") val minimum: MetricTemperatureResponse,
    @SerializedName("Maximum") val maximum: MetricTemperatureResponse,
)

data class ForecastDayResponse(
    @SerializedName("Icon") val icon: Int,
)

fun ForecastResponse.toDomain() = forecasts.map {
    Forecast(
        dateTime = it.date.toOffsetDateTime(),
        minTemperature = "${it.temperature.minimum.value}°${it.temperature.minimum.unit}",
        maxTemperature = "${it.temperature.maximum.value}°${it.temperature.maximum.unit}",
        condition = WeatherCondition.fromCode(it.day.icon),
    )
}