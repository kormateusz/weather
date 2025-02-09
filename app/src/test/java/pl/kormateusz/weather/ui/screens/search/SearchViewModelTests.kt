package pl.kormateusz.weather.ui.screens.search

import app.cash.turbine.turbineScope
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.domain.usecases.GetLocallySavedLocationsUseCase
import pl.kormateusz.weather.domain.usecases.GetLocationsUseCase
import pl.kormateusz.weather.domain.usecases.SaveSelectedLocationLocallyUseCase
import pl.kormateusz.weather.domain.usecases.ValidateSearchQueryUseCase
import pl.kormateusz.weather.ui.navigation.MainRouting

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTests {

    @Mock
    private lateinit var mainRouting: MainRouting

    @Mock
    private lateinit var validateSearchQueryUseCase: ValidateSearchQueryUseCase

    @Mock
    private lateinit var getLocationsUseCase: GetLocationsUseCase

    @Mock
    private lateinit var saveSelectedLocationLocallyUseCase: SaveSelectedLocationLocallyUseCase

    @Mock
    private lateinit var getLocallySavedLocationsUseCase: GetLocallySavedLocationsUseCase

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    private fun setupViewModel(savedLocations: List<Location> = emptyList()) = runTest {
        whenever(getLocallySavedLocationsUseCase.execute()).thenReturn(savedLocations)
        viewModel = SearchViewModel(
            mainRouting,
            validateSearchQueryUseCase,
            getLocationsUseCase,
            saveSelectedLocationLocallyUseCase,
            getLocallySavedLocationsUseCase,
        )
    }

    @Test
    fun `valid query triggers location search`() = runTest {
        // given
        val query = "Kraków"
        val result = listOf(
            Location("Kraków", "1"),
            Location("Warszawa", "2"),
        )
        whenever(validateSearchQueryUseCase.execute(query)).thenReturn(true)
        whenever(getLocationsUseCase.execute(query)).thenAnswer { Result.success(result) }
        setupViewModel()

        // when
        viewModel.onQueryChange(query)

        // then
        turbineScope {
            val turbine = viewModel.state.testIn(backgroundScope)
            val loadingState = turbine.awaitItem()
            val expectedLoadingState = SearchScreenUIState(
                searchQuery = query,
                isQueryValid = true,
                isLoading = true,
                isErrorVisible = false,
                locations = emptyList()
            )
            assertEquals(expectedLoadingState, loadingState)

            val loadedState = turbine.awaitItem()
            val expectedLoadedState = SearchScreenUIState(
                searchQuery = query,
                isQueryValid = true,
                isLoading = false,
                isErrorVisible = false,
                locations = result
            )
            assertEquals(expectedLoadedState, loadedState)
        }
    }

    @Test
    fun `invalid query does not trigger location search and returns empty list`() = runTest {
        // given
        val query = "123."
        val savedLocations = listOf(
            Location("Kraków", "1"),
            Location("Warszawa", "2"),
        )
        whenever(validateSearchQueryUseCase.execute(query)).thenReturn(false)
        setupViewModel(savedLocations)

        // when
        viewModel.onQueryChange(query)

        // then
        val state = viewModel.state.value
        val expectedState = SearchScreenUIState(
            searchQuery = query,
            isQueryValid = false,
            isLoading = false,
            isErrorVisible = false,
            locations = emptyList()
        )
        assertEquals(expectedState, state)
    }

    @Test
    fun `empty query does not trigger location search and returns saved list`() = runTest {
        // given
        val query = ""
        val savedLocations = listOf(
            Location("Kraków", "1"),
            Location("Warszawa", "2"),
        )
        whenever(validateSearchQueryUseCase.execute(query)).thenReturn(true)
        setupViewModel(savedLocations)

        // when
        viewModel.onQueryChange(query)

        // then
        val state = viewModel.state.value
        val expectedState = SearchScreenUIState(
            searchQuery = query,
            isQueryValid = true,
            isLoading = true,
            isErrorVisible = false,
            locations = savedLocations
        )
        assertEquals(expectedState, state)
    }

    @Test
    fun `valid query but API error shows error state`() = runTest {
        // given
        val query = "Kraków"
        whenever(validateSearchQueryUseCase.execute(query)).thenReturn(true)
        whenever(getLocationsUseCase.execute(query)).thenAnswer {
            Result.failure<List<Location>>(Exception())
        }
        setupViewModel()

        // when
        viewModel.onQueryChange(query)

        // then
        turbineScope {
            val turbine = viewModel.state.testIn(backgroundScope)
            val loadingState = turbine.awaitItem()
            val expectedLoadingState = SearchScreenUIState(
                searchQuery = query,
                isQueryValid = true,
                isLoading = true,
                isErrorVisible = false,
                locations = emptyList()
            )
            assertEquals(expectedLoadingState, loadingState)

            val errorState = turbine.awaitItem()
            val expectedErrorState = SearchScreenUIState(
                searchQuery = query,
                isQueryValid = true,
                isLoading = false,
                isErrorVisible = true,
                locations = emptyList()
            )
            assertEquals(expectedErrorState, errorState)
        }
    }

    @Test
    fun `navigates to details screen and saves selected location`() {
        // given
        val location = Location("Warszawa", "1")
        setupViewModel()

        // when
        viewModel.onLocationClick(location)

        // then
        verify(saveSelectedLocationLocallyUseCase).execute(location)
        verify(mainRouting).navigateToDetailsScreen(location)
    }
}