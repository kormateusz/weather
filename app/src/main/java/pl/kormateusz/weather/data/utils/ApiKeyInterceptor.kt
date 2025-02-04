package pl.kormateusz.weather.data.utils

import okhttp3.Interceptor
import okhttp3.Response
import pl.kormateusz.weather.BuildConfig

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newUrl = request.url.newBuilder()
            .addQueryParameter(API_KEY_QUERY_PARAMETER, BuildConfig.API_KEY)
            .build()
        val newRequest = request.newBuilder().url(newUrl).build()

        return chain.proceed(newRequest)
    }

    private companion object {
        private const val API_KEY_QUERY_PARAMETER = "apikey"
    }
}