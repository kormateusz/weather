package pl.kormateusz.weather

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.kormateusz.weather.modules.appModule
import pl.kormateusz.weather.modules.networkModule
import pl.kormateusz.weather.modules.repositoryModule
import pl.kormateusz.weather.modules.routingModule
import pl.kormateusz.weather.modules.useCaseModule
import pl.kormateusz.weather.modules.viewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                appModule,
                viewModelModule,
                routingModule,
                useCaseModule,
                repositoryModule,
                networkModule,
            )
        }
    }
}