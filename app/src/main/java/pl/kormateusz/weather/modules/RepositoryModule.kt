package pl.kormateusz.weather.modules

import org.koin.dsl.module
import pl.kormateusz.weather.data.repositories.LocationRepositoryImpl
import pl.kormateusz.weather.data.repositories.WeatherRepositoryImpl
import pl.kormateusz.weather.domain.repositories.LocationRepository
import pl.kormateusz.weather.domain.repositories.WeatherRepository

val repositoryModule = module {
    single<LocationRepository> {
        LocationRepositoryImpl(
            locationService = get(),
            locationDao = get(),
        )
    }
    single<WeatherRepository> { WeatherRepositoryImpl(weatherService = get()) }
}