package pl.kormateusz.weather.ui.navigation

import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.ui.screens.details.DetailsScreen

class MainRouting : BaseRouting() {
    fun navigateToDetailsScreen(location: Location) {
        navigateTo(DetailsScreen(location))
    }
}