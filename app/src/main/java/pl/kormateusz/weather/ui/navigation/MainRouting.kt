package pl.kormateusz.weather.ui.navigation

import pl.kormateusz.weather.ui.screens.details.DetailsScreen

class MainRouting : BaseRouting() {
    fun navigateToDetailsScreen(value: String) {
        navigateTo(DetailsScreen(value))
    }
}