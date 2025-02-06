package pl.kormateusz.weather.ui.navigation

import pl.kormateusz.weather.ui.navigation.extensions.ComposeRoute
import pl.kormateusz.weather.ui.navigation.extensions.ComposeRouteNavigator

object NavigateUp : ComposeRoute

abstract class BaseRouting {
    fun navigateTo(route: ComposeRoute) {
        ComposeRouteNavigator.navigateTo(route)
    }

    fun navigateUp() {
        ComposeRouteNavigator.navigateTo(NavigateUp)
    }
}