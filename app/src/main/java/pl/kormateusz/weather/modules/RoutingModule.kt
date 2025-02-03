package pl.kormateusz.weather.modules

import org.koin.dsl.module
import pl.kormateusz.weather.ui.navigation.MainRouting

val routingModule = module {
    factory { MainRouting() }
}