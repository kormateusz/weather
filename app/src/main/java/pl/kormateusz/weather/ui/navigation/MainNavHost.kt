package pl.kormateusz.weather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import pl.kormateusz.weather.ui.screens.details.DetailsScreen
import pl.kormateusz.weather.ui.screens.details.DetailsViewModel
import pl.kormateusz.weather.ui.screens.search.SearchScreen
import pl.kormateusz.weather.ui.screens.search.SearchViewModel

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
        composable<DetailsScreen> {
            val detailsScreen: DetailsScreen = it.toRoute()
            val viewModel = koinViewModel<DetailsViewModel> { parametersOf(detailsScreen.value) }
            DetailsScreen(viewModel)
        }
    }
}