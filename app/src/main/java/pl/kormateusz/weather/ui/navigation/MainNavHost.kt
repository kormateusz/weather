package pl.kormateusz.weather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import pl.kormateusz.weather.domain.models.Location
import pl.kormateusz.weather.ui.navigation.extensions.serializableType
import pl.kormateusz.weather.ui.screens.details.DetailsScreen
import pl.kormateusz.weather.ui.screens.details.DetailsViewModel
import pl.kormateusz.weather.ui.screens.search.SearchScreen
import pl.kormateusz.weather.ui.screens.search.SearchViewModel
import kotlin.reflect.typeOf

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = SearchScreen,
    ) {
        composable<SearchScreen> {
            val viewModel = koinViewModel<SearchViewModel>()
            SearchScreen(viewModel)
        }
        composable<DetailsScreen>(
            typeMap = mapOf(typeOf<Location>() to serializableType<Location>())
        ) {
            val detailsScreen: DetailsScreen = it.toRoute()
            val viewModel = koinViewModel<DetailsViewModel> { parametersOf(detailsScreen.location) }
            DetailsScreen(viewModel)
        }
    }
}