package pl.kormateusz.weather.modules

import org.koin.dsl.module
import pl.kormateusz.weather.data.database.AppDatabase
import pl.kormateusz.weather.data.database.getAppDatabase

val databaseModule = module {
    single { getAppDatabase(get()) }
    single { get<AppDatabase>().getLocationDao() }
}