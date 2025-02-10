package pl.kormateusz.weather.ui.screens.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.serialization.Serializable
import pl.kormateusz.weather.R
import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.domain.models.WeatherCondition
import pl.kormateusz.weather.ui.navigation.extensions.ComposeRoute
import pl.kormateusz.weather.ui.screens.common.EmptyState
import pl.kormateusz.weather.ui.screens.common.FullScreenLoader

@Serializable
data class DetailsScreen(val location: Location) : ComposeRoute

@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {
    val state: DetailsUIState by viewModel.state.collectAsState()

    DetailsScreenBody(
        state,
        onBackClick = { viewModel.onBackButtonClick() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenBody(state: DetailsUIState, onBackClick: () -> Unit) {
    val animatedColor by animateColorAsState(
        targetValue = state.condition.color,
        label = "color"
    )

    Scaffold(
        containerColor = animatedColor,
        topBar = {
            TopAppBar(
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White,
                ),
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when {
            state.isLoading -> FullScreenLoader(color = Color.White)

            state.isErrorVisible -> EmptyState(
                icon = R.drawable.ic_error,
                text = R.string.unknown_exception,
                color = Color.White,
            )

            else -> LoadedBody(innerPadding, state)
        }
    }
}

@Composable
private fun LoadedBody(
    innerPadding: PaddingValues,
    state: DetailsUIState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp),
    ) {
        Text(
            text = state.locationName,
            color = Color.White,
            fontSize = 36.sp,
        )
        Text(
            text = state.dateTime,
            color = Color.White,
            fontSize = 14.sp,
        )
        Image(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .offset(x = 100.dp),
            painter = painterResource(state.condition.image),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.temperature,
            textAlign = TextAlign.End,
            color = Color.White,
            fontSize = 64.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = state.weatherText,
            textAlign = TextAlign.End,
            color = Color.White,
            fontSize = 24.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        AnimatedVisibility(state.forecastItems.isNotEmpty()) {
            Column {
                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = Color.White)
                Spacer(modifier = Modifier.height(24.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    items(state.forecastItems) { item ->
                        ForecastItem(item)
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastItem(itemState: ForecastItemUIState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = itemState.date,
            color = Color.White,
            fontSize = 14.sp,
        )
        Image(
            modifier = Modifier
                .size(48.dp)
                .padding(4.dp),
            painter = painterResource(itemState.condition.image),
            contentDescription = null,
        )
        Text(
            text = itemState.minTemperature,
            color = Color.White,
            fontSize = 14.sp,
        )
        Text(
            text = itemState.maxTemperature,
            color = Color.White,
            fontSize = 14.sp,
        )
    }

}

@Composable
@Preview(showSystemUi = true)
private fun DetailsScreenPreview() {
    DetailsScreenBody(
        state = DetailsUIState(
            locationName = "Kraków",
            dateTime = "26 February 2024, 15:16",
            temperature = "20°C",
            weatherText = "It's pouring like crazy",
            forecastItems = listOf(
                ForecastItemUIState(
                    date = "27 Feb",
                    minTemperature = "22°C",
                    maxTemperature = "26°C",
                    condition = WeatherCondition.RAIN,
                ),
                ForecastItemUIState(
                    date = "28 Feb",
                    minTemperature = "24°C",
                    maxTemperature = "28°C",
                    condition = WeatherCondition.SUNNY,
                ),
                ForecastItemUIState(
                    date = "29 Feb",
                    minTemperature = "26°C",
                    maxTemperature = "30°C",
                    condition = WeatherCondition.CLOUDY,
                ),
                ForecastItemUIState(
                    date = "30 Feb",
                    minTemperature = "24°C",
                    maxTemperature = "28°C",
                    condition = WeatherCondition.SNOW,
                ),
                ForecastItemUIState(
                    date = "31 Feb",
                    minTemperature = "26°C",
                    maxTemperature = "30°C",
                    condition = WeatherCondition.WINDY,
                ),
            ),
        ),
        onBackClick = {},
    )
}

@Composable
@Preview(showSystemUi = true)
private fun DetailsScreenPreviewLoading() {
    DetailsScreenBody(
        state = DetailsUIState(isLoading = true),
        onBackClick = {},
    )
}

@Composable
@Preview(showSystemUi = true)
private fun DetailsScreenPreviewError() {
    DetailsScreenBody(
        state = DetailsUIState(isErrorVisible = true),
        onBackClick = {},
    )
}