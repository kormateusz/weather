package pl.kormateusz.weather.ui.screens.search

import pl.kormateusz.weather.domain.models.Location

data class SearchScreenUIState(
    val searchQuery: String = "",
    val isQueryValid: Boolean = true,
    val isLoading: Boolean = false,
    val locations: List<Location> = emptyList(),
)
