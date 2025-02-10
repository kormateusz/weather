package pl.kormateusz.weather.ui.screens.details

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
import pl.kormateusz.weather.domain.extensions.DateTimeFormatter
import pl.kormateusz.weather.domain.models.Forecast
import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.domain.models.Weather
import pl.kormateusz.weather.domain.models.WeatherCondition
import pl.kormateusz.weather.domain.usecases.GetForecastForLocationUseCase
import pl.kormateusz.weather.domain.usecases.GetWeatherForLocationUseCase
import pl.kormateusz.weather.ui.navigation.MainRouting
import java.time.OffsetDateTime
import java.time.ZoneOffset

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTests {

    @Mock
    private lateinit var mainRouting: MainRouting

    @Mock
    private lateinit var dateTimeFormatter: DateTimeFormatter

    @Mock
    private lateinit var getWeatherForLocationUseCase: GetWeatherForLocationUseCase

    @Mock
    private lateinit var getForecastForLocationUseCase: GetForecastForLocationUseCase

    private lateinit var viewModel: DetailsViewModel

    private val testLocation = Location(name = "Kraków", key = "123")

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    private fun setupViewModel() {
        viewModel = DetailsViewModel(
            testLocation,
            mainRouting,
            dateTimeFormatter,
            getWeatherForLocationUseCase,
            getForecastForLocationUseCase
        )
    }

    @Test
    fun `init fetches weather successfully and updates state`() = runTest {
        // given
        val weather = Weather(
            dateTime = OffsetDateTime.of(2025, 2, 9, 10, 0, 0, 0, ZoneOffset.UTC),
            temperature = "15°C",
            weatherText = "Sunny",
            condition = WeatherCondition.SUNNY,
        )
        val forecast = Forecast(
            dateTime = OffsetDateTime.of(2025, 2, 10, 10, 0, 0, 0, ZoneOffset.UTC),
            minTemperature = "15°C",
            maxTemperature = "15°C",
            condition = WeatherCondition.MOSTLY_SUNNY
        )
        whenever(getWeatherForLocationUseCase.execute(testLocation.key))
            .thenAnswer { (Result.success(weather)) }
        whenever(dateTimeFormatter.toFullDate(weather.dateTime))
            .thenReturn("8th Feb 2025, 10:00 AM")
        whenever(dateTimeFormatter.toShortDate(forecast.dateTime))
            .thenReturn("8th Feb")
        whenever(getForecastForLocationUseCase.execute(testLocation.key))
            .thenAnswer { Result.success(listOf(forecast)) }
        setupViewModel()

        // then
        turbineScope {
            val turbine = viewModel.state.testIn(backgroundScope)
            val loadingState = turbine.awaitItem()
            val expectedLoadingState = DetailsUIState(
                locationName = "Kraków",
                isLoading = true,
                isErrorVisible = false,
            )
            assertEquals(expectedLoadingState, loadingState)

            val loadedState = turbine.awaitItem()
            val expectedLoadedState = DetailsUIState(
                locationName = "Kraków",
                isLoading = false,
                isErrorVisible = false,
                dateTime = "8th Feb 2025, 10:00 AM",
                temperature = "15°C",
                weatherText = "Sunny",
                condition = WeatherCondition.SUNNY,
            )
            assertEquals(expectedLoadedState, loadedState)

            val updatedState = turbine.awaitItem()
            val expectedUpdatedState = DetailsUIState(
                locationName = "Kraków",
                isLoading = false,
                isErrorVisible = false,
                dateTime = "8th Feb 2025, 10:00 AM",
                temperature = "15°C",
                weatherText = "Sunny",
                condition = WeatherCondition.SUNNY,
                forecastItems = listOf(
                    ForecastItemUIState(
                        date = "8th Feb",
                        minTemperature = "15°C",
                        maxTemperature = "15°C",
                        condition = WeatherCondition.MOSTLY_SUNNY,
                    )
                )
            )
            assertEquals(expectedUpdatedState, updatedState)
        }
    }

    @Test
    fun `init handles weather fetch failure and sets error state`() = runTest {
        // given
        whenever(getWeatherForLocationUseCase.execute(testLocation.key))
            .thenAnswer { Result.failure<Weather>(Exception()) }
        whenever(getForecastForLocationUseCase.execute(testLocation.key))
            .thenAnswer { Result.success(emptyList<Forecast>()) }

        setupViewModel()

        // then
        turbineScope {
            val turbine = viewModel.state.testIn(backgroundScope)
            val loadingState = turbine.awaitItem()
            val expectedLoadingState = DetailsUIState(
                locationName = "Kraków",
                isLoading = true,
                isErrorVisible = false,
            )
            assertEquals(expectedLoadingState, loadingState)

            val errorState = turbine.awaitItem()
            val expectedErrorState = DetailsUIState(
                locationName = "Kraków",
                isLoading = false,
                isErrorVisible = true,
            )
            assertEquals(expectedErrorState, errorState)
        }
    }

    @Test
    fun `onBackButtonClick triggers navigation back`() = runTest {
        // given
        whenever(getWeatherForLocationUseCase.execute(testLocation.key))
            .thenAnswer { Result.failure<Weather>(Exception()) }
        whenever(getForecastForLocationUseCase.execute(testLocation.key))
            .thenAnswer { Result.success(emptyList<Forecast>()) }
        setupViewModel()

        // when
        viewModel.onBackButtonClick()

        // then
        verify(mainRouting).navigateUp()
    }
}