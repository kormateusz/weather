package pl.kormateusz.weather.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.domain.usecases.GetLocationsUseCase
import pl.kormateusz.weather.domain.usecases.ValidateSearchQueryUseCase
import pl.kormateusz.weather.ui.navigation.MainRouting

class SearchViewModel(
    private val mainRouting: MainRouting,
    private val validateSearchQueryUseCase: ValidateSearchQueryUseCase,
    private val getLocationsUseCase: GetLocationsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchScreenUIState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    fun onQueryChange(query: String) {
        val isQueryValid = validateSearchQueryUseCase.execute(query)
        _state.update {
            it.copy(
                searchQuery = query,
                isQueryValid = isQueryValid,
                isLoading = isQueryValid,
                isErrorVisible = false,
                locations = if (isQueryValid) it.locations else emptyList()
            )
        }
        searchJob?.cancel()
        if (!isQueryValid) return
        getLocations(query)
    }

    private fun getLocations(query: String) {
        searchJob = viewModelScope.launch {
            delay(300)
            getLocationsUseCase.execute(query)
                .onSuccess { locations ->
                    _state.update {
                        it.copy(
                            locations = locations,
                            isLoading = false,
                            isErrorVisible = false
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isErrorVisible = true
                        )
                    }
                }
        }
    }

    fun onLocationClick(location: Location) {
        mainRouting.navigateToDetailsScreen(location.name)
    }
}