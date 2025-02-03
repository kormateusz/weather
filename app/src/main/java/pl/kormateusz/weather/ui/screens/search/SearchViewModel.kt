package pl.kormateusz.weather.ui.screens.search

import androidx.lifecycle.ViewModel
import pl.kormateusz.weather.ui.navigation.MainRouting

class SearchViewModel(
    private val mainRouting: MainRouting,
) : ViewModel() {

    fun onButtonClicked() {
        mainRouting.navigateToDetailsScreen("Kraków")
    }
}