package pl.kormateusz.weather.ui.screens.details

import androidx.lifecycle.ViewModel
import pl.kormateusz.weather.ui.navigation.MainRouting

class DetailsViewModel(
    val value: String,
    private val mainRouting: MainRouting,
) : ViewModel() {

    fun onButtonClicked() {
        mainRouting.navigateUp()
    }
}