package pl.kormateusz.weather.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import pl.kormateusz.weather.R
import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.ui.navigation.extensions.ComposeRoute
import pl.kormateusz.weather.ui.screens.common.EmptyState
import pl.kormateusz.weather.ui.screens.common.SearchBox

@Serializable
object SearchScreen : ComposeRoute

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val state: SearchScreenUIState by viewModel.state.collectAsState()

    SearchScreenBody(
        state,
        onQueryChange = { viewModel.onQueryChange(it) },
        onLocationClick = { viewModel.onLocationClick(it) },
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchScreenBody(
    state: SearchScreenUIState,
    onQueryChange: (String) -> Unit,
    onLocationClick: (Location) -> Unit,
) {
    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            Surface(shadowElevation = 4.dp) {
                Column {
                    CenterAlignedTopAppBar(
                        modifier = Modifier.padding(vertical = 32.dp),
                        title = {
                            Text(
                                text = stringResource(id = R.string.app_name),
                                style = MaterialTheme.typography.headlineLarge,
                            )
                        })

                    SearchBox(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 4.dp),
                        value = state.searchQuery,
                        isErrorVisible = !state.isQueryValid,
                        errorText = R.string.search_box_error,
                        placeholder = stringResource(id = R.string.search_placeholder),
                        onTextChanged = { onQueryChange(it) },
                    )

                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(if (state.isLoading) 1f else 0f),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            when {
                state.isErrorVisible -> EmptyState(
                    icon = R.drawable.ic_error,
                    text = R.string.unknown_exception,
                )

                state.locations.isEmpty() -> EmptyState(
                    icon = R.drawable.ic_search,
                    text = R.string.search_empty_state_label,
                )

                else -> LazyColumn(
                    contentPadding = PaddingValues(top = 16.dp)
                ) {
                    items(state.locations) { location ->
                        LocationListItem(location, onItemClick = { onLocationClick(it) })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun SearchScreenPreview() {
    SearchScreenBody(
        state = SearchScreenUIState(
            isLoading = true,
            locations = listOf(
                Location("Kraków", "Key"),
                Location("Warsaw", "Key")
            )
        ),
        onQueryChange = {},
        onLocationClick = {},
    )
}

@Composable
@Preview(showSystemUi = true)
private fun SearchScreenEmptyStatePreview() {
    SearchScreenBody(
        state = SearchScreenUIState(),
        onQueryChange = {},
        onLocationClick = {},
    )
}

@Composable
@Preview(showSystemUi = true)
private fun SearchScreenErrorStatePreview() {
    SearchScreenBody(
        state = SearchScreenUIState(isErrorVisible = true),
        onQueryChange = {},
        onLocationClick = {},
    )
}