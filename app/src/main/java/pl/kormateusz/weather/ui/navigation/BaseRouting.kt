package pl.kormateusz.weather.ui.navigation

object NavigateUp : ComposeRoute

abstract class BaseRouting {
    fun navigateTo(route: ComposeRoute) {
        ComposeRouteNavigator.navigateTo(route)
    }

    fun navigateUp() {
        ComposeRouteNavigator.navigateTo(NavigateUp)
    }
}