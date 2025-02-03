package pl.kormateusz.weather.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import pl.kormateusz.weather.ui.navigation.ComposeRoute

@Serializable
data class DetailsScreen(val value: String) : ComposeRoute

@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {
    Column {
        Text(text = viewModel.value)
        Button(onClick = { viewModel.onButtonClicked() }) {
            Text(text = "Back")
        }
    }
}