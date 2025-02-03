package pl.kormateusz.weather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

interface ComposeRoute

object ComposeRouteNavigator {
    private val _route = MutableSharedFlow<ComposeRoute>(
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1,
    )
    val route: SharedFlow<ComposeRoute> = _route

    fun navigateTo(event: ComposeRoute) {
        _route.tryEmit(event)
    }
}

/**
 * This composable LaunchedEffect collects navigation events
 * from the [ComposeRouteNavigator] and navigates to the next
 * emitted screen.
 */
@Composable
fun NavigatorLaunchedEffect(
    navController: NavHostController,
) {
    LaunchedEffect("NavigationEvents") {
        ComposeRouteNavigator.route.collect { screen ->
            when (screen) {
                is NavigateUp -> navController.navigateUp()
                else -> navController.navigate(screen)
            }
        }
    }
}