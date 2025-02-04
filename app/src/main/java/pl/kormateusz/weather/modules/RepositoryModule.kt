package pl.kormateusz.weather.modules

import org.koin.dsl.module
import pl.kormateusz.weather.data.repositories.LocationRepositoryImpl
import pl.kormateusz.weather.domain.repositories.LocationRepository

val repositoryModule = module {
    single<LocationRepository> { LocationRepositoryImpl(locationService = get()) }
}