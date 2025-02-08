package pl.kormateusz.weather.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kormateusz.weather.domain.extensions.DateTimeFormatter
import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.domain.usecases.GetWeatherForLocationUseCase
import pl.kormateusz.weather.ui.navigation.MainRouting

class DetailsViewModel(
    private val location: Location,
    private val mainRouting: MainRouting,
    private val dateTimeFormatter: DateTimeFormatter,
    private val getWeatherForLocationUseCase: GetWeatherForLocationUseCase,
) : ViewModel() {

    private val _state =
        MutableStateFlow(DetailsUIState(locationName = location.name, isLoading = true))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getWeatherForLocationUseCase.execute(location.key)
                .onSuccess { weather ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            dateTime = dateTimeFormatter.toFullDate(weather.dateTime),
                            temperature = weather.temperature,
                            weatherText = weather.weatherText,
                            condition = weather.condition,
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isErrorVisible = true,
                        )
                    }
                }

        }
    }

    fun onBackButtonClick() {
        mainRouting.navigateUp()
    }
}