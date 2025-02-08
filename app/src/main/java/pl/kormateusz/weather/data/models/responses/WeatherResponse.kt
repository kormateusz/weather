package pl.kormateusz.weather.data.models.responses

import com.google.gson.annotations.SerializedName
import pl.kormateusz.weather.domain.extensions.toLocalDateTime
import pl.kormateusz.weather.domain.models.Weather
import pl.kormateusz.weather.domain.models.WeatherCondition

data class WeatherResponse(
    @SerializedName("LocalObservationDateTime") val dateTime: String,
    @SerializedName("WeatherText") val weatherText: String,
    @SerializedName("Temperature") val temperatureResponse: WhetherTemperatureResponse,
    @SerializedName("WeatherIcon") val weatherIcon: Int,
)

data class WhetherTemperatureResponse(
    @SerializedName("Metric") val metricTemperatureResponse: MetricTemperatureResponse
)

data class MetricTemperatureResponse(
    @SerializedName("Value") val value: Double,
    @SerializedName("Unit") val unit: String
)

fun WeatherResponse.toDomain() = Weather(
    dateTime = dateTime.toLocalDateTime(),
    weatherText = weatherText,
    temperature = "${temperatureResponse.metricTemperatureResponse.value}°${temperatureResponse.metricTemperatureResponse.unit}",
    condition = WeatherCondition.fromCode(weatherIcon)
)