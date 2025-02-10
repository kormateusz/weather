package pl.kormateusz.weather.ui.screens.details

import pl.kormateusz.weather.domain.models.WeatherCondition

data class DetailsUIState(
    val locationName: String = "",
    val dateTime: String = "",
    val temperature: String = "",
    val weatherText: String = "",
    val isLoading: Boolean = false,
    val isErrorVisible: Boolean = false,
    val condition: WeatherCondition = WeatherCondition.UNKNOWN,
    val forecastItems: List<ForecastItemUIState> = emptyList()
)

data class ForecastItemUIState(
    val date: String = "",
    val minTemperature: String = "",
    val maxTemperature: String = "",
    val condition: WeatherCondition = WeatherCondition.UNKNOWN
)
