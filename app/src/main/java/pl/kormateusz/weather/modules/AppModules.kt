package pl.kormateusz.weather.modules

import org.koin.dsl.module
import pl.kormateusz.weather.domain.extensions.DateTimeFormatter

val appModule = module {
    single { DateTimeFormatter() }
}