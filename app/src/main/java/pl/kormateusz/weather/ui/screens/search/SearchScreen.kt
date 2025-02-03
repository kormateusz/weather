package pl.kormateusz.weather.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable
import pl.kormateusz.weather.ui.navigation.ComposeRoute

@Serializable
object SearchScreen : ComposeRoute

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    Column {
        Text(text = "Search Screen")
        Button(onClick = { viewModel.onButtonClicked() }) {
            Text(text = "Open details")
        }
    }
}