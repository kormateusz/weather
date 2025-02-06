package pl.kormateusz.weather.ui.screens.details

data class DetailsUIState(
    val locationName: String = "",
    val dateTime: String = "",
    val temperature: String = "",
    val weatherText: String = "",
)
