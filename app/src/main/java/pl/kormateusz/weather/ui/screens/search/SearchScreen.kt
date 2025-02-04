package pl.kormateusz.weather.ui.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.serialization.Serializable
import pl.kormateusz.weather.R
import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.ui.navigation.ComposeRoute
import pl.kormateusz.weather.ui.screens.common.SearchBox

@Serializable
object SearchScreen : ComposeRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val state: SearchScreenUIState by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(vertical = 32.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            SearchBox(
                modifier = Modifier.zIndex(1f),
                value = state.searchQuery,
                isErrorVisible = !state.isQueryValid,
                errorText = R.string.search_box_error,
                placeholder = stringResource(id = R.string.search_placeholder),
                onTextChanged = { viewModel.onQueryChange(it) },
            )

            if (state.locations.isEmpty()) {
                EmptyState()
            } else {
                BodyList(state, viewModel)
            }
        }
    }
}

@Composable
private fun BodyList(
    state: SearchScreenUIState,
    viewModel: SearchViewModel
) {
    LazyColumn(
        modifier = Modifier.offset(y = (-16).dp),
        contentPadding = PaddingValues(top = 32.dp)
    ) {
        items(state.locations) { location ->
            ListItem(location, onItemClick = { viewModel.onLocationClick(it) })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ListItem(
    location: Location,
    onItemClick: (Location) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        onClick = { onItemClick(location) },
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(54.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background),
                painter = painterResource(R.drawable.city),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = location.name,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier.size(120.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.search_empty_state_label),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}