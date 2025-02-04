package pl.kormateusz.weather.modules

import org.koin.dsl.module
import pl.kormateusz.weather.domain.usecases.ValidateSearchQueryUseCase

val useCaseModule = module {
    factory { ValidateSearchQueryUseCase() }
}