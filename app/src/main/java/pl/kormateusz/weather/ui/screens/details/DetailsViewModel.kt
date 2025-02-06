package pl.kormateusz.weather.ui.screens.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.ui.navigation.MainRouting

class DetailsViewModel(
    private val location: Location,
    private val mainRouting: MainRouting,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsUIState(locationName = location.name))
    val state = _state.asStateFlow()

    fun onBackButtonClick() {
        mainRouting.navigateUp()
    }
}