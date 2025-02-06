package pl.kormateusz.weather.modules

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import pl.kormateusz.weather.BuildConfig
import pl.kormateusz.weather.data.services.LocationService
import pl.kormateusz.weather.data.services.WeatherService
import pl.kormateusz.weather.data.utils.ApiKeyInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 60L

val networkModule = module {
    single { ApiKeyInterceptor() }
    single { createApiOkHttpBuilder(get<ApiKeyInterceptor>()) }
    single { createApiService<LocationService>(get()) }
    single { createApiService<WeatherService>(get()) }
}

private inline fun <reified T> createApiService(okHttpClient: OkHttpClient): T =
    Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(T::class.java)


fun createApiOkHttpBuilder(interceptor: Interceptor? = null): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .apply { interceptor?.let { addInterceptor(it) } }
        .build()
}