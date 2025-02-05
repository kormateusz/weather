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
import pl.kormateusz.weather.domain.usecases.GetLocationsUseCase
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

    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = SearchViewModel(mainRouting, validateSearchQueryUseCase, getLocationsUseCase)
    }

    @Test
    fun `onQueryChange with valid query triggers location search`() = runTest {
        // given
        val query = "Kraków"
        val result = listOf(
            Location("Kraków", "1"),
            Location("Warszawa", "2"),
        )
        whenever(validateSearchQueryUseCase.execute(query)).thenReturn(true)
        whenever(getLocationsUseCase.execute(query)).thenAnswer { Result.success(result) }

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
    fun `onQueryChange with invalid query does not trigger location search`() = runTest {
        // given
        val query = ""
        whenever(validateSearchQueryUseCase.execute(query)).thenReturn(false)

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
    fun `onQueryChange with valid query but API error shows error state`() = runTest {
        // given
        val query = "Kraków"
        whenever(validateSearchQueryUseCase.execute(query)).thenReturn(true)
        whenever(getLocationsUseCase.execute(query)).thenAnswer {
            Result.failure<List<Location>>(Exception())
        }

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
    fun `onLocationClick navigates to details screen`() {
        // given
        val location = Location("Warszawa", "1")

        // when
        viewModel.onLocationClick(location)

        // then
        verify(mainRouting).navigateToDetailsScreen("Warszawa")
    }
}