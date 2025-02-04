package pl.kormateusz.weather.ui.screens.search

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.domain.usecases.ValidateSearchQueryUseCase
import pl.kormateusz.weather.ui.navigation.MainRouting

class SearchViewModel(
    private val mainRouting: MainRouting,
    private val validateSearchQueryUseCase: ValidateSearchQueryUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchScreenUIState())
    val state = _state.asStateFlow()

    fun onQueryChange(query: String) {
        _state.update {
            it.copy(
                searchQuery = query,
                isQueryValid = validateSearchQueryUseCase.execute(query),
                locations = if (query == "Kraków") listOf(Location("Kraków")) else emptyList()
            )
        }
    }

    fun onLocationClick(location: Location) {
        mainRouting.navigateToDetailsScreen(location.name)
    }
}