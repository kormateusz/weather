package pl.kormateusz.weather.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import pl.kormateusz.weather.ui.navigation.MainNavHost
import pl.kormateusz.weather.ui.navigation.NavigatorLaunchedEffect
import pl.kormateusz.weather.ui.theme.WeatherTheme

@Composable
fun MainApp() {
    val navController = rememberNavController()
    NavigatorLaunchedEffect(navController)

    WeatherTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            MainNavHost(navController)
        }
    }
}