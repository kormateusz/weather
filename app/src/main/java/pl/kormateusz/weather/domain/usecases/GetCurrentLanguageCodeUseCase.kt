package pl.kormateusz.weather.domain.usecases

import java.util.Locale

class GetCurrentLanguageCodeUseCase : BaseUseCase<String>() {
    override fun buildUseCase(): String = Locale.getDefault().toLanguageTag()
}