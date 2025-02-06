package pl.kormateusz.weather.modules

import org.koin.dsl.module
import pl.kormateusz.weather.domain.usecases.GetCurrentLanguageCodeUseCase
import pl.kormateusz.weather.domain.usecases.GetLocationsUseCase
import pl.kormateusz.weather.domain.usecases.GetWeatherForLocationUseCase
import pl.kormateusz.weather.domain.usecases.ValidateSearchQueryUseCase

val useCaseModule = module {
    factory { ValidateSearchQueryUseCase() }
    factory { GetCurrentLanguageCodeUseCase() }
    factory {
        GetLocationsUseCase(
            locationRepository = get(),
            getCurrentLanguageCodeUseCase = get()
        )
    }
    factory {
        GetWeatherForLocationUseCase(
            weatherRepository = get(),
            getCurrentLanguageCodeUseCase = get()
        )
    }
}